package com.royalfoods.tastytables.factory;

import com.royalfoods.tastytables.data.dto.*;
import com.royalfoods.tastytables.data.model.*;
import com.royalfoods.tastytables.factory.abstrcts.*;

import java.util.*;
import java.util.stream.*;

import static java.util.Optional.of;


public  class UserDtoBuilder  extends AbstractUserBuilderFactory {
    private String fullName;
    private String roles;

    @Override
    public <T> Optional<T> build() {
        UserDto userDto = new UserDto();
        userDto.setFullName(firstName + " " + lastName);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setRoles(roles);
        return (Optional<T>) of(userDto);
    }

    @Override
    public UserDtoBuilder withUser(Object o) {
        User entity = (User) o;
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        this.fullName = entity.getFirstName() +" " +entity.getLastName();
        this.withRoles(entity.getRoles());
        return this;
    }

    @Override
    public AbstractUserBuilderFactory withRoles(Object o) {
        Set<Role> eRoles = (Set<Role>) o;
        this.roles = eRoles
                .stream()
                .map(Role::getRoleName)
                .collect(Collectors.joining(","));
        return this;
    }
}
