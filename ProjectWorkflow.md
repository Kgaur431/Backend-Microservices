
#### Continuation 

```
        we have added "implementation 'org.springframework.boot:spring-boot-starter-security'" this in build.gradle file 
            because if we want to use JWT token then we need to add this dependency 
                            
                            Disclamer:  project built on bottom to top approach. 
                            
            1.  we added the about package to add the about controller. 
            2.  we added the Application.yaml file to define the server and all.
            3.  we added the security package to add the appsecurityconfig class.
                 3.1    appsecurityconfig class extends the websecurityconfigureadapter class.
                 3.2    added the configure method in appsecurityconfig class.                  -----   step 1.
            4.  we added the user package for login & signup.
                4.1   added the user controller.   
                4.2   added the CreateUserDto class.
                4.3   added the user entity class. 
                4.4   added the loginUserDto class.
                4.5   added the user repository.
                4.6   added the user service. 
                        4.6.1    added createUser()
                        4.6.2    added verifyUser()
                4.7   added the modal mapper in build.gradle file.
                        4.7.1   added the bean for model mapper in main class. 
                4.8   added the implementation of verifyUser() & createUser in user service.
                        4.8.1   added the model mapper in UserService class & initialize it in constructor.
                        4.8.2   findbyYsername() method is not provided by jpa so we need to create it in user repository.
                4.9   added the signup & login method in user controller class. 
                        4.9.1   added the UserResponsedDto class.
                        4.9.2   added the implementation of signup & login method in user controller class.
            5.  update the below details in application.yaml file.
                        like:-
                                spring:
                                  datasource:
                                     driver-class-name: org.h2.Driver
                                        url: jdbc:h2:file:./testdb
                                  jpa:
                                    show-sql: true
            6.  update the AppSecurityConfig class.
                        6.1   new edpoint like register || login which are currently not permitted for all users. so we need to permit them.
                        6.2   permit for login & signup. so we need to add new antMatchers() for login & signup.
            7.  Implement the JWT using BCryptPasswordEncoder
                        7.1   create a Bean for password Encoder in main class.
                        7.2   now we encode the password while creating the user in user service class using password encoder.
                                7.2.1   when we encode the password & set it to user entity. at the time of registration.
                                7.2.2   but when we login then we need to decode the password in the verifyUser() method.
            8.  we update the UserResponseDto class. (now we are implementing the token so that we can return the token to the user.)
                        8.1   we add the token field in UserResponseDto class. (token can be server side or JWT token)
                        8.2   we create AuthTokenEntity class for returning the token. 
                                8.2.1   first we return the server side token in the form of UserResponseDto class.
                        8.3   we create the AuthTokenRepository class.
                        8.4   we create the AuthTokenService class.
                                8.4.1   implemnets the AuthTokenService class 
            9.  we update the UserService class.
                        9.1   while creating the user we also create the token for that user. (check AuthToken.md) 
                        9.2   added createToken() method in AuthTokenService class.
                        9.3   added getUserFromToken() method in AuthTokenService class.
                        9.4   use the createToken() method in createUser() method & getUserFromToken() method in verifyUser() method.
                        *     (read the AuthToken.md file, two tokens are created.) 
            10. Enable the Security Config so that those endpoints which need authentication in those places if we send the token then it work.
                        10.1  
            11. add the depedency to access the JWT Library that will generate the jwt tokens for us.
                        11.1   add the dependency in build.gradle file.
                                (for JWT we are not creating the Entity class & Repository class.)
                        11.2  add the Jwt service class.    (follow the link for more clarity https://github.com/auth0/java-jwt)
                                11.2.1  implements the JwtService class. (we can also add expriy and so on.)
                        11.3  add createToken() method in JwtService class.
                        11.4  add getUserFromToken() method in JwtService class.
            12. added the JWT implementation in UserSerivce class. 
            
                        
                        
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            

                    
                                
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
``` 