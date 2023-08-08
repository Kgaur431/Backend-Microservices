package com.kartik.backend_microservices.user;

import com.kartik.backend_microservices.security.authtoken_server_side.AuthTokenService;
import com.kartik.backend_microservices.security.jwt.JwtService;
import com.kartik.backend_microservices.user.dtos.CreateUserDto;
import com.kartik.backend_microservices.user.dtos.LoginUserDto;
import com.kartik.backend_microservices.user.dtos.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
//    @Autowired
//    private  UserRepository userRepository;
//    @Autowired
//    private  ModelMapper modelMapper;


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    // @Autowired
    public AuthTokenService authTokenService;
    // @Autowired        // if we have commented this because we are using constructor injection in AuthTokenServiceTest class. because here we are using @DataJpaTest so it will not create the service
    public JwtService jwtService;




    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthTokenService authTokenService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authTokenService = authTokenService;
        this.jwtService = jwtService;
    }
    public UserResponseDto createUser(CreateUserDto createUserDto) {
        UserEntity userEntity = modelMapper.map(createUserDto, UserEntity.class);
        userEntity.setPassword((passwordEncoder.encode(createUserDto.getPassword())));
        UserEntity savedUserEntity = userRepository.save(userEntity);
        UserResponseDto response = modelMapper.map(savedUserEntity, UserResponseDto.class);
        // server side token generation
        // String token = authTokenService.createToken(savedUserEntity);
        //response.setToken(token);

        // jwt token generation
        String token = jwtService.createJwtToken(savedUserEntity.getUsername());
        response.setToken(token);
        return response;
    }

    public UserResponseDto verifyUser(LoginUserDto loginUserDto) {
        UserEntity userEntity = userRepository.findByUsername(loginUserDto.getUsername());
        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }
        if (!passwordEncoder.matches(loginUserDto.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Password is incorrect");                        // hashed password matching
        }
        // Server side token generation
        UserResponseDto response = modelMapper.map(userEntity, UserResponseDto.class);
        response.setToken(authTokenService.createToken(userEntity));
        return response;
    }

    public UserResponseDto getUserByUsername(String username){
        UserEntity user = userRepository.findByUsername(username);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        return modelMapper.map(user, UserResponseDto.class);
    }


}
