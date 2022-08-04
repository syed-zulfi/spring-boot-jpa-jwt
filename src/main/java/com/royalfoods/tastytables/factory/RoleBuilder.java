package com.royalfoods.tastytables.factory;

import com.fasterxml.jackson.databind.*;
import com.royalfoods.tastytables.data.dto.*;
import com.royalfoods.tastytables.data.model.*;
import com.royalfoods.tastytables.factory.abstrcts.*;

import java.util.*;

public class RoleBuilder extends AbstractModelBuilderFactory {
    private String roleName;
    private String roleDesc;
    private Object role;
    private Class roleOf;


    @Override
    public <T> Optional build() {
        if(role!=null){
            return Optional.ofNullable(role);
        } else {
            if(null != roleOf && roleOf.getName().equalsIgnoreCase(RoleDto.class.getName())) {
                RoleDto role = new RoleDto();
                role.setRoleDesc(roleDesc);
                role.setRoleName(roleName);
                return Optional.ofNullable(role);
            } else {
                Role role = new Role();
                role.setRoleDesc(roleDesc);
                role.setRoleName(roleName);
                return Optional.ofNullable(role);
            }
        }
    }

    public RoleBuilder withRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public RoleBuilder withRoleDesc(String roleDec) {
        this.roleDesc = roleDec;
        return this;
    }

    public RoleBuilder withModel(Object srcModel,Class destType){
        ObjectMapper mapper = new ObjectMapper();
        role = mapper.convertValue(srcModel,destType);
        return this;

    }
}
