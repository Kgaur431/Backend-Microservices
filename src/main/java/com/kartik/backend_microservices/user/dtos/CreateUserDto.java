package com.kartik.backend_microservices.user.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private String username;
    private String email;
    private String password;
}

/*
    we have added these @AllArgsConstructor, @NoArgsConstructor becoz we are writing the test for auth token
        for creating that we need user service object so for that we need to create user object
        so while creating we need to pass the values so we need to create the constructor @
        but in our logic we have passed the not passed any arguments while creating the user object so we use @NoArgsConstructor
            so that it will not fail while creating the user object

 */
