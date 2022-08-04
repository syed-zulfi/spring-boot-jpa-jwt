package com.royalfoods.tastytables.service;

import com.royalfoods.tastytables.config.*;
import com.royalfoods.tastytables.data.dto.*;
import com.royalfoods.tastytables.data.model.*;
import com.royalfoods.tastytables.exception.*;
import com.royalfoods.tastytables.factory.*;
import com.royalfoods.tastytables.repository.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

@Service
@AllArgsConstructor
public class RoleService {
   private RoleRepository roleRepository;
   private BuilderFactoryBeans builderFactoryBeans;

   public ApiResponseDto createRole(RoleDto roleDto){
       roleRepository.findByRoleName(roleDto.getRoleName()).ifPresent(e-> {
           throw new UniqueRecordConstraintViolationException(String.format("Role %s already available",roleDto.getRoleName()));
       });
       Role role = (Role) builderFactoryBeans.roleBuilder().withModel(roleDto, Role.class).build().get();
       roleRepository.save(role);
       return (ApiResponseDto) ServiceResponseBuilder
               .create(HttpStatus.CREATED,"Role created",roleDto)
               .build()
               .get();
   }
}
