package com.royalfoods.tastytables.factory;

import com.royalfoods.tastytables.data.dto.*;
import com.royalfoods.tastytables.data.model.*;
import com.royalfoods.tastytables.exception.*;
import com.royalfoods.tastytables.factory.abstrcts.*;
import com.royalfoods.tastytables.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.*;

import java.util.*;
import java.util.stream.*;
import static java.util.Optional.of;

public  class UserEntityBuilder extends AbstractUserBuilderFactory {
    private Set<Role> roles = new HashSet<>();
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserEntityBuilder withUser(Object o) {
        UserDto dto = (UserDto) o;
        this.email = dto.getEmail();
        userRepository.findByEmail(email)
                .ifPresent(e->{throw
                               new UniqueRecordConstraintViolationException
                                ("User already available with email "
                                        +email);});
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.password = passwordEncoder.encode(dto.getPassword());
        this.withRoles(dto.getRoles());
        return this;
    }

    @Override
    public Optional<User> build() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRoles(roles);
        user.setPassword(password);
        return of(user);
    }

    @Override
    public UserEntityBuilder withRoles(Object o) {
        String dRoles = (String) o;
        Optional<List<String>> roleList = Optional
                .ofNullable(dRoles)
                .map(e->Arrays.asList(e.split(",")));
        roleList.ifPresent(e->{
            this.roles = roleRepository
                    .findByRoleNameIn(e)
                    .stream()
                    .collect(Collectors.toSet());
        });

        return this;
    }
}
