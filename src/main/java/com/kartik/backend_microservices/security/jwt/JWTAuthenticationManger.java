package com.kartik.backend_microservices.security.jwt;

import com.kartik.backend_microservices.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationManger implements  AuthenticationManager {

    private JwtService jwtService;
    private UserService userService;


    public JWTAuthenticationManger(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof JWTAuthentication){
            JWTAuthentication jwtAuthentication = (JWTAuthentication) authentication;
            var jwtString = jwtAuthentication.getCredentials();
            // TODO: Crypto failure an jwt verification failure.
            // TODO: Check if jwt is expired.
            var username = jwtService.getUserFromToken(jwtString);
            var user = userService.getUserByUsername(username);
            jwtAuthentication.setUser(user);
            return jwtAuthentication;
        }
        return null;
    }
}

/**
 *  why this type casting is done?
 *                  JWTAuthentication jwtAuthentication = (JWTAuthentication) authentication;
 *   becoz, when we read the credentials then it should be read as string becoz default authentication object type has everything as object
 *          becoz of generic interface.
 *          In JWT Credentials, credentials are in string format.
 *   In jwtAuthenticationManager, we are dealing with JWTAuthentication object, so we have to type cast it.
 */