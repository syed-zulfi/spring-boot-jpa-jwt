package com.royalfoods.tastytables.service.util;

import com.auth0.jwt.*;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.*;
import com.auth0.jwt.interfaces.*;
import com.royalfoods.tastytables.config.*;
import com.royalfoods.tastytables.data.model.*;
import com.royalfoods.tastytables.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.security.oauth2.resource.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.stereotype.*;
import sun.jvm.hotspot.oops.*;

import javax.servlet.http.*;
import javax.validation.*;
import java.nio.charset.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

@Component
public class JWTUtils {
    private Algorithm algorithm = Algorithm.HMAC256("royal".getBytes());
    @Value("${access.token.expiration:10800000}")
    private Long tokenExpiration;
    @Value("${refresh.token.expiration:21600000}")
    private Long refreshTokenExpiration;
    @Autowired
    private  BuilderFactoryBeans builderFactoryBeans;
    public static final String ACCESS_TOKEN = "access-token";
    public static final String REFRESH_TOKEN = "refresh-token";
    public Map<String,String> generateToken(User user, HttpServletRequest request){

        Map<String,String> tokenMap = new HashMap<>();
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withIssuer(request.getRequestURL().toString())
                .withExpiresAt(Date.from(Instant.now().plusMillis(tokenExpiration)))
                .withClaim("roles",user
                        .getAuthorities()
                        .stream()
                        .map(e->e.getAuthority())
                        .collect(Collectors.toList())).
                        sign(algorithm);

        String refreshToken = JWT.create()
                .withExpiresAt(Date.from(Instant.now().plusMillis(refreshTokenExpiration)))
                .withSubject(user.getUsername())
                .withIssuer(request.getRequestURL().toString())
                .withIssuedAt(Date.from(Instant.now()))
                .sign(algorithm);
        tokenMap.put(ACCESS_TOKEN,accessToken);
        tokenMap.put(REFRESH_TOKEN,refreshToken);

        return tokenMap;
    }

    public User parseJwt(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT =  verifier.verify(token);
        UserEntityBuilder userEntityBuilder = (UserEntityBuilder) builderFactoryBeans.userEntityBuilder()
                .withEmail(decodedJWT.getSubject());
        List<String> rolesList = decodedJWT.getClaim("roles").asList(String.class);
        User user = userEntityBuilder.build().get();
       user.setRoles(rolesList.stream().map(e->new Role(e)).collect(Collectors.toSet()));
        return user;
    }


}
