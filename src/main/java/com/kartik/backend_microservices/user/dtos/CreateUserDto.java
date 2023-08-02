package com.kartik.backend_microservices.user.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
@Data
public class CreateUserDto {
    private String username;
    private String email;
    private String password;
}
