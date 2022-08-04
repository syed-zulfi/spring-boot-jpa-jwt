package com.royalfoods.tastytables.service;

import com.royalfoods.tastytables.data.dto.*;
import com.royalfoods.tastytables.data.model.*;
import com.royalfoods.tastytables.exception.*;
import com.royalfoods.tastytables.factory.*;
import com.royalfoods.tastytables.service.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import javax.servlet.http.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    public ApiResponseDto authenticate(LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
        log.info("Authenticating {}",loginDto.getUsername());
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));
            Map<String,String> token = jwtUtils.generateToken((User)authentication.getPrincipal(),request);
            response.setHeader(JWTUtils.ACCESS_TOKEN,token.get(JWTUtils.ACCESS_TOKEN));
            response.setHeader(JWTUtils.REFRESH_TOKEN,token.get(JWTUtils.REFRESH_TOKEN));

            return (ApiResponseDto) ServiceResponseBuilder.create(HttpStatus.OK,String.format("Authentication Success %S", loginDto.getUsername()),token).build().get();
        }catch (AuthenticationException authenticationException) {
            log.error("Authentication Failed for {}",loginDto.getUsername());
            throw new ApiSecurityException(String.format("Authentication failed for %S",loginDto.getUsername()),authenticationException);
        }
    }
}
