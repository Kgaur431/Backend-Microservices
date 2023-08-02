package com.kartik.backend_microservices.user.dtos;

import lombok.Data;

@Data
public class LoginUserDto {
    private String username;
    private String password;
}
