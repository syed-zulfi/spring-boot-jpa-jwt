package com.royalfoods.tastytables.service;

import com.royalfoods.tastytables.config.*;
import com.royalfoods.tastytables.data.dto.*;
import com.royalfoods.tastytables.data.model.*;
import com.royalfoods.tastytables.exception.*;
import com.royalfoods.tastytables.factory.*;
import com.royalfoods.tastytables.repository.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.*;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    UserRepository userRepository;
    BuilderFactoryBeans factoryBeans;
    RoleRepository roleRepository;




@Transactional
    public ApiResponseDto<UserDto> createUser(UserDto userDto) throws RuntimeException {
    /**
     * using userEntityBuilder object to convert UserDto to User Entity object
     * for processing the user data create operation.
     */

    User user = userRepository
            .save(factoryBeans.userEntityBuilder()
                    .withUser(userDto)
                    .build()
                    .orElseThrow(()->new NullObjectException("Required... Object Not Populated")));
    /**
     *  Setting user object in roles, as User and Role entities are
     *  in bidirectional many to many association
     */
    user
            .getRoles()
            .stream()
            .map(Role::getUsers)
            .forEach(e->e.add(user));

    /**
     * Using userDtoBuilder to convert user entity object to user dto object for output
     */
    userDto = (UserDto) factoryBeans.userDtoBuilder()
              .withUser(user)
              .build()
              .orElseThrow(()->new NullObjectException("Object not found"));

    Optional<ApiResponseDto<UserDto>> clientResponse =  factoryBeans
                                                                    .restResponseDtoBuilder()
                                                                    .withHttpStatus(HttpStatus.CREATED)
                                                                    .withMessage("User Created")
                                                                    .withBody(userDto)
                                                                    .build();

    if (clientResponse.isPresent()){
        return clientResponse.get();
    } else {
        throw new NullObjectException("Object Not Found");
    }
    }

    public ApiResponseDto<List<UserDto>> getAll(){
            List<UserDto> dtoList = new ArrayList<>();
                    userRepository.findAll()
                    .stream()
                    .forEach((e)->{
                       UserDto uDto = (UserDto) factoryBeans.
                               userDtoBuilder().withUser(e).build().get();
                       dtoList.add(uDto);
                    });

            return (ApiResponseDto<List<UserDto>>)
                          factoryBeans.restResponseDtoBuilder()
                                  .withMessage("User list retrived")
                                  .withHttpStatus(HttpStatus.OK)
                                  .withBody(dtoList).build().get();
    }

    public ApiResponseDto getUserFrom(String email){
        log.info("******User Dto builder*****");
         User user = userRepository.findByEmail(email)
                 .orElseThrow(()->new NullObjectException("Data not found"));
         return (ApiResponseDto) factoryBeans.restResponseDtoBuilder()
                 .withBody(factoryBeans
                         .userDtoBuilder()
                         .withUser(user)
                         .build()
                         .get())
                 .withMessage("User Found")
                 .withHttpStatus(HttpStatus.OK)
                 .build()
                 .get();

    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResponseDto addRoleToUser(UserDto userDto) {
        Set<Role> roles = roleRepository
                .findByRoleNameIn(Arrays
                        .asList(Optional.ofNullable(userDto.getRoles().split(","))
                                .orElseThrow(()-> new NullObjectException("Comma separated roles required in body"))))
                .stream().collect(Collectors.toSet());


        /*List<Role> roleList = roles
                .stream()
                .map(e->(Role)factoryBeans
                        .roleBuilder()
                        .withRoleName(e)
                        .build()
                        .get())
                .collect(Collectors.toList());
*/

    User user = userRepository
            .findByEmail(Optional.ofNullable(userDto.getEmail())
                    .orElseThrow(()->new NullObjectException("Email id required is null")))
            .orElseThrow(()-> new NullObjectException("User Not found for given email"));

    roles.stream().forEach(e->e.getUsers().add(user));
    user.setRoles(roles);
    return (ApiResponseDto) ServiceResponseBuilder
            .create(HttpStatus.CREATED,String.format("Roles %S added to user %S",userDto.getRoles(),userDto.getEmail()),null)
            .build().get();
    }
}
