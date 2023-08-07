package com.kartik.backend_microservices.security.jwt;

import com.kartik.backend_microservices.user.dtos.UserResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuthentication implements Authentication {

    private String jwtString;
    private UserResponseDto user;

    public JWTAuthentication(String jwtString) {
        this.jwtString = jwtString;
    }

    void setUser(UserResponseDto user) {
        this.user = user;
    }

    /**
     * getAuthorities() method is basically, given an authenticated object which all things it has authorities to.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO: not needed for now, unless we have different resources accessible to different roles.
        return null;
    }

    /**
     * getCredentials() method, this is where we return the string/token that is used for authentication.
     */
    @Override
    public String getCredentials() {
        return jwtString;
    }

    @Override
    public Object getDetails() {
        // TODO: not needed for now.
        return null;
    }

    /**
     * getPrincipal() method, this is where we return the user object that is used for authentication.
     *                        this is where we return the user/client who is getting authenticated.
     */
    @Override
    public UserResponseDto getPrincipal() {
        return user;
    }


    @Override
    public boolean isAuthenticated() {
        return (user != null);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    }

    @Override
    public String getName() {
        return null;
    }
}