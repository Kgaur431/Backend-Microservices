package com.kartik.backend_microservices.security.jwt;


import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFilter;


public class JWTAuthenticationFilter extends AuthenticationFilter {
    public JWTAuthenticationFilter(JWTAuthenticationManger authenticationManager) {
        super(authenticationManager, new JWTAuthenticationConverter());

        this.setSuccessHandler((request, response, authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        });
    }

}