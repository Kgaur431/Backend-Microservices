

``` 
        Why do we need Auth Token ?
            whenever user is getting created like 
                old code:-
                          public UserResponseDto createUser(CreateUserDto createUserDto) {
                                UserEntity userEntity = modelMapper.map(createUserDto, UserEntity.class);
                                userEntity.setPassword((passwordEncoder.encode(createUserDto.getPassword())));
                                return modelMapper.map(userRepository.save(userEntity), UserResponseDto.class);
                          }
                new code:-
                          public UserResponseDto createUser(CreateUserDto createUserDto) {
                            UserEntity userEntity = modelMapper.map(createUserDto, UserEntity.class);
                            userEntity.setPassword((passwordEncoder.encode(createUserDto.getPassword())));
                            UserEntity savedUserEntity = userRepository.save(userEntity);
                            UserResponseDto response = modelMapper.map(savedUserEntity, UserResponseDto.class);
                            String token = authTokenService.createToken(savedUserEntity);
                            response.setToken(token);
                            return response;
                          }
                    
                        user has been saved then we also do is we create the token for that user (so that next time when user send any req
                        then we can verify that token and we can allow the user to access the resource that's why we need to create the token.)
                        with the userEntity & set the token to the response object & return the response object.
            when we verify the user then we do something similar
                old code:-
                          public UserResponseDto verifyUser(LoginUserDto loginUserDto) {
                            UserEntity userEntity = userRepository.findByUsername(loginUserDto.getUsername());
                            if (userEntity == null) {
                                throw new RuntimeException("User not found");
                            }
                            //  if (!userEntity.getPassword().equals(loginUserDto.getPassword())) {           // due to storing plain text password it won't match.
                            //        throw new RuntimeException("Password is incorrect");
                            //   }
                            if (!passwordEncoder.matches(loginUserDto.getPassword(), userEntity.getPassword())) {
                                throw new RuntimeException("Password is incorrect");                        // hashed password matching
                            }
                            return  modelMapper.map(userEntity, UserResponseDto.class);
                          }
                      
                new code:-
                          public UserResponseDto verifyUser(LoginUserDto loginUserDto) {
                            UserEntity userEntity = userRepository.findByUsername(loginUserDto.getUsername());
                            if (userEntity == null) {
                                throw new RuntimeException("User not found");
                            }
                            if (!passwordEncoder.matches(loginUserDto.getPassword(), userEntity.getPassword())) {
                                throw new RuntimeException("Password is incorrect");                        // hashed password matching
                            }
                            UserResponseDto response = modelMapper.map(userEntity, UserResponseDto.class);
                            response.setToken(authTokenService.createToken(userEntity));
                            return response; 
                          }           
                           we are creating the token with the userEntity & we are setting the token to the response object & returning the response object.
                            why we are creating the token with the userEntity while verifying the user ?
                                because we need to create the token with the userEntity so that we can verify the user with the token.
                            
                                
                        
                        
                        
                        
                        



        In AuthTokenService class, 
        
                  public String createToken(UserEntity userEntity) {
                        var tokenEntity = new AuthTokenEntity();        // spring will not inject this dependency, so we have to create it manually
                        tokenEntity.setUser(userEntity);
                         authTokenRepository.save(tokenEntity);
                        return tokenEntity.getToken();
                  }
                  
                  this above method is to create the Server side token. (Server side token is nothing but the ID of the AuthTokenEntity class)
                    & this token will be create by server when user is getting created. when user creating then in userService class we are calling 
                    the createToken() method of the AuthTokenService class.
                    so we are just creating the TokenEntity class object and we are setting the userEntity with respect to that tokenEntity.
                    
        
        Working (two tokens are created for single user)
            URL:-   http://localhost:8282/user/register
            Method: POST
            task:-  register
            body:-
                    {
                            "email":"123d@g.ocm",
                        "password":"123456789",
                        "username":"starc"
                    }
            response:-
                    {
                        "username": "starc",
                        "email": "123d@g.ocm",
                        "id": 1,
                        "token": "316766a0-bdd4-4858-be9d-b6f41b97bb6a"
                    }
                    
                    
                
            URL:-   http://localhost:8282/user/login
            Method: POST
            task:-  login
            body:-
                    {
                        "username":"starc",
                        "password":"123456789"
                    }
            response:-
                    {
                        "username": "starc",
                        "email": "123d@g.ocm",
                        "id": 1,
                        "token": "436f8281-3e53-421e-a970-f53129439668"
                    }

            so for user id 1, two tokens have been created from the token we get to know that who the user is.
            these tokens are created by the server calleds UUID tokens.
            
            from this token we can find user back ?
                we able to if we use the getUserFromToken() method of the AuthTokenService class.
                
            where tokens is coming from ?
               it will come from the api header. so we can find the who the user is. 

        In AuthTokenEntity class we can add other fields also 
            like:-
                To set the expiry of the token.
                created at, 
                        ... etc. 

        
        JWT token:-
                    if we want to verify the token manually 
                    then go to the jwt.io website & paste the token in the encoded section & Secret key in the verify signature section.
                    then we can see the payload of the token.

    











```

