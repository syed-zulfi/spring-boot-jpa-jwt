package com.royalfoods.tastytables.security.filter;

import com.auth0.jwt.*;
import com.auth0.jwt.exceptions.*;
import com.fasterxml.jackson.databind.*;
import com.royalfoods.tastytables.data.dto.error.*;
import com.royalfoods.tastytables.data.model.*;
import com.royalfoods.tastytables.exception.*;
import com.royalfoods.tastytables.service.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.apache.commons.lang3.*;
import org.apache.tomcat.util.http.parser.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtBasedAuthorizationFilter extends OncePerRequestFilter {
    final String BEARER = "Bearer ";
    final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Looking up for authorization token in request header");
        Optional<String> jwtAuthToken = Optional.ofNullable(request.getHeader(AUTHORIZATION)).filter(e -> StringUtils.startsWith(e, BEARER));
        try {
            if (jwtAuthToken.isPresent()) {
                String token = StringUtils.substringAfter(jwtAuthToken.get(), BEARER);
                User user = jwtUtils.parseJwt(token);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("Authorization token found,parsing token {}", token);
            } else {
                log.info("Authorization not found in request {}", request.getRequestURI());
            }
            filterChain.doFilter(request, response);
        } catch(TokenExpiredException tokenExpiredException) {
            ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE,"Requested with expired token, please login again.");
            apiError.setDetailsMessage(tokenExpiredException.getLocalizedMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getOutputStream(),apiError);

        }
    }
}
