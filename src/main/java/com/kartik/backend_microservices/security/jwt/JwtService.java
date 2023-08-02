 package com.kartik.backend_microservices.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

 @Service
public class JwtService {
    public static final String SECERET = "kmjkoip86l36uhyntg8rnbf47vdg6gsgsgsbvebj";
    Algorithm algorithm = Algorithm.HMAC256(SECERET);

    public String createJwtToken(String username) {
            return JWT.create()
                    .withSubject(username)
                    .withIssuedAt(new Date())
                    .sign(algorithm);

    }

    public String getUserFromToken(String token) {
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
    }

}
