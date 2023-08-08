package com.kartik.backend_microservices.security.authtoken_server_side;

import com.kartik.backend_microservices.security.jwt.JwtService;
import com.kartik.backend_microservices.user.UserEntity;
import com.kartik.backend_microservices.user.UserRepository;
import com.kartik.backend_microservices.user.UserService;
import com.kartik.backend_microservices.user.dtos.CreateUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DataJpaTest // The main logic is We can directly make an object of the services not via autowire so anything that can be annotated with repository that things we can do @Autowired
public class AuthTokenServiceTest {
    // @DataJpaTest doing this test not creating the service so we can't autowire service here, we will create it manually
    //@Autowired
    private  AuthTokenService authTokenService;

    @Autowired
    private UserRepository  userRepository;
    @Autowired
    private AuthTokenRepository authTokenRepository;
    //@Autowired
    private UserService userService;
    private JwtService jwtService;

    @BeforeEach
    public void setup(){        // we make sure manually that services should be injected. so we are doing constructor injection here
        authTokenService = new AuthTokenService(authTokenRepository, userRepository );
        jwtService = new JwtService();
        userService = new UserService(userRepository, new ModelMapper(), new BCryptPasswordEncoder(), authTokenService, jwtService);
    }

    @Test
    public void createAuthToken_works_with_userentity() {
        userService.createUser(                 /**   this we can create in @BeforeEachTest so that it will create before all the test run */
                new CreateUserDto(
                        "arnav",
                        "arnav123@gmail.com",
                        "arnav123"
                )
        );
        UserEntity userEntity = userRepository.findByUsername("arnav");
        String token = authTokenService.createToken(userEntity);

    }
}
