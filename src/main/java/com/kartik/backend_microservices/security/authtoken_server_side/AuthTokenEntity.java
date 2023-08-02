package com.kartik.backend_microservices.security.authtoken_server_side;

import com.kartik.backend_microservices.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "auth_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID token;

    @ManyToOne
    private UserEntity user;        // for one user, there are multiple tokens so many-to-one relationships

}
