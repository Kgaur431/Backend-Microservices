package com.kartik.backend_microservices.security.authtoken_server_side;

import com.kartik.backend_microservices.user.UserEntity;
import com.kartik.backend_microservices.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenService {
    // @Autowired   for creating text we have commented & used constructor injection
    private AuthTokenRepository authTokenRepository;

    // @Autowired
    private UserRepository userRepository;

    public AuthTokenService(AuthTokenRepository authTokenRepository, UserRepository userRepository)
    {
        this.userRepository = userRepository;
        this.authTokenRepository = authTokenRepository;
    }



    public String createToken(UserEntity userEntity) {
        AuthTokenEntity tokenEntity = new AuthTokenEntity();        // spring will not inject this dependency, so we have to create it manually
        tokenEntity.setUser(userEntity);
         authTokenRepository.save(tokenEntity);
        return tokenEntity.getToken().toString();
    }

    public UserEntity getUserFromToken(String token){
        AuthTokenEntity authTokenEntity = authTokenRepository.findById(token).orElseThrow(() -> new RuntimeException("Invalid token"));
        return authTokenEntity.getUser();
    }






}
