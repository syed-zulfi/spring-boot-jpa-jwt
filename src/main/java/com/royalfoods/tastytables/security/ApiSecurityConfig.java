package com.royalfoods.tastytables.security;

import com.royalfoods.tastytables.exception.*;
import com.royalfoods.tastytables.security.filter.*;
import com.royalfoods.tastytables.service.util.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.http.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;

import javax.management.*;

@Configuration
@RequiredArgsConstructor
public class ApiSecurityConfig {
    final private SecurityUserService userDetailsService;
    final private PasswordEncoder passwordEncoder;
    final private JWTUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    final private JwtBasedAuthorizationFilter jwtBasedAuthorizationFilter;


    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        try {
            final String LOGIN_URL = "/guest/v1/authenticate";
            authenticationManager = authenticationManager(httpSecurity.getSharedObject(AuthenticationConfiguration.class));
            CustomAuthenticationFilter authFilter = new CustomAuthenticationFilter(authenticationManager,jwtUtils);
            authFilter.setFilterProcessesUrl(LOGIN_URL);
            httpSecurity.csrf().disable();
            httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            httpSecurity.authorizeRequests().antMatchers(RoleAuths.PUBLIC.getUrlNameSpace()).permitAll();
            httpSecurity.authorizeRequests().antMatchers(RoleAuths.ADMIN.getUrlNameSpace()).hasAnyAuthority(RoleAuths.ADMIN.getRoleName());
            httpSecurity.authorizeRequests().antMatchers(RoleAuths.USER.getUrlNameSpace()).hasAnyAuthority(RoleAuths.USER.getRoleName(),RoleAuths.ADMIN.getRoleName());
            httpSecurity.authorizeRequests().anyRequest().authenticated();
            httpSecurity.addFilter(authFilter);
            httpSecurity.addFilterBefore(jwtBasedAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
            return httpSecurity.build();
        } catch (Exception e) {
            throw new ApiSecurityException("Security exception found",e);
        }
    }

    @Bean
    @Order(1)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }




}
