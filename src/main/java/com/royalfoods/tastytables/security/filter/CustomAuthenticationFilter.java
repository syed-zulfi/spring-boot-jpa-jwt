package com.royalfoods.tastytables.security.filter;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.*;
import com.fasterxml.jackson.databind.*;
import com.royalfoods.tastytables.data.dto.*;
import com.royalfoods.tastytables.data.dto.error.*;
import com.royalfoods.tastytables.data.model.*;
import com.royalfoods.tastytables.exception.*;
import com.royalfoods.tastytables.factory.*;
import com.royalfoods.tastytables.service.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.stream.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JWTUtils jwtUtils;
    String userName;

    private  AuthenticationManager authenticationManager;

   public CustomAuthenticationFilter(AuthenticationManager authenticationManager,JWTUtils jwtUtils) {
       this.authenticationManager = authenticationManager;
       this.jwtUtils = jwtUtils;
   }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("...Attempting Authentication...");
        String payload = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        ObjectMapper mapper = new ObjectMapper();
        LoginDto loginDto = mapper.readValue(payload,LoginDto.class);
         userName = loginDto.getUsername();
        String passWord = loginDto.getPassword();
        log.info("User Name:{}",userName);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName,passWord);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("Authentication Success...");log.info("Generating token");
        User user = (User)authResult.getPrincipal();
        Map<String,String> tokenMap = jwtUtils.generateToken(user,request);
        response.setHeader("access_token",tokenMap.get(JWTUtils.ACCESS_TOKEN));
        response.setHeader("refresh_token",tokenMap.get(JWTUtils.REFRESH_TOKEN));
        response.setContentType(APPLICATION_JSON_VALUE);
        ApiResponseDto<Map> tokenResponse = new ApiResponseDto<>(HttpStatus.ACCEPTED,"Login Success",tokenMap);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),tokenResponse);
        log.info("Token generation success");
        log.debug("Token generation success {}",tokenMap.get(JWTUtils.ACCESS_TOKEN));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,String.format("Login faild for %S due to bad credentials",userName));
        apiError.setDetailsMessage(failed.getLocalizedMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),apiError);
   }
}
