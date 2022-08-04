package com.royalfoods.tastytables.config;

import com.royalfoods.tastytables.factory.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.*;

@Configuration
public class BuilderFactoryBeans {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public UserDtoBuilder userDtoBuilder(){
        return new UserDtoBuilder();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public UserEntityBuilder userEntityBuilder(){
        return new UserEntityBuilder();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RestResponseDtoBuilder restResponseDtoBuilder(){
        return new RestResponseDtoBuilder();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RoleBuilder roleBuilder(){
        return new RoleBuilder();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
