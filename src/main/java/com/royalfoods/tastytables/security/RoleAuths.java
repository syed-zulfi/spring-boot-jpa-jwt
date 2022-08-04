package com.royalfoods.tastytables.security;

import lombok.*;

import java.util.*;
import java.util.stream.*;

public enum RoleAuths {
    ADMIN("ROLE_ADMIN",new ArrayList<String>(){
        {
            add("/admin/**");
        }}),
    USER("ROLE_USER",new ArrayList<String>(){{
        add("/user/**");
    }}),
    PUBLIC("ROLE_PUBLIC", new ArrayList<String>(){
        {
            add("/guest/**");
            add("/login/**");
        }});

    private String roleName;
    private List<String> urlPrefix;

     RoleAuths(String roleName,List<String> urlPrefix){
        this.roleName = roleName;
        this.urlPrefix = urlPrefix;
    }

    public String getRoleName() {
        return roleName;
    }

    public String[] getUrlNameSpace() {

        return urlPrefix.stream().toArray(String[]::new);
    }
    public List<String> getUrlPrefix(){
         return urlPrefix;
    }
}
