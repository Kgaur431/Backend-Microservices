package com.kartik.backend_microservices.user;

import com.kartik.backend_microservices.user.dtos.CreateUserDto;
import com.kartik.backend_microservices.user.dtos.LoginUserDto;
import com.kartik.backend_microservices.user.dtos.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

//    private  UserService userService;

    @Autowired
    private  UserService userService;

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody CreateUserDto createUserDto) {
           UserResponseDto userResponseDto = userService.createUser(createUserDto);
           return ResponseEntity.created(URI.create("/user/" + userResponseDto.getId())).body(userResponseDto);
    }


    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> verifyUser(@RequestBody LoginUserDto loginUserDto) {
        UserResponseDto userResponseDto = userService.verifyUser(loginUserDto);
        return ResponseEntity.status(200).body(userResponseDto);
    }


}

/*
                @Autowired
                private final UserService userService;

                 Ques:- if we add final keyword in above line, why spring is not injecting the dependency after using @Autowired annotation?
                 Ans :-     Because final keyword does not allow the dependency to be injected and hence we have to use constructor injection.
                            so we have to do either constructor injection or setter injection. like we did in userService class.
                               or
                                    @Autowired
                                    private  UserService userService;

                                   @Autowired
                                   private UserService userService;

               find root cause of the problem:-
                       due to used of @Autowired annotation, spring should inject the dependency but it is not injecting the dependency.
                       so the object is not created and hence we are getting null therefore hibernate is not able to insert the data into the database.

                       so we must know what we are writing in the code and what is the meaning of that code.






 */
