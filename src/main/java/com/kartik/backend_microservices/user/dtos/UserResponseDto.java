package com.kartik.backend_microservices.user.dtos;

import lombok.Data;

@Data
public class UserResponseDto {
    private String username;
    private String email;
    private Long id;
    private String token;
}
