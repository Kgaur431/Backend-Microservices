package com.kartik.backend_microservices.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

import javax.servlet.http.HttpServletRequest;

public class JWTAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
       String header =  request.getHeader("Authorization");
       if (header != null &&  !header.isEmpty() && header.startsWith("Bearer ")){
          String token = header.substring(7);       // 7 is the length of "Bearer " so the token will start from 7th index like 0,1,2,3,4,5,6,7 means 8th index
            return new JWTAuthentication(token);            // we are returning the JWTAuthentication object with token that it will send to the JWTAuthenticationManager
       }
        return null;
    }
}

