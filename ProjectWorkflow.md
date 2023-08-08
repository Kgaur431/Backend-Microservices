
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
                (till now we have used httpBasic() now we want token based authentication so we use SessionManagement. session will use token automatically).
            13. instead of using httpBasic() now we are using JWT( the AppSecurityConfig class).
                        13.2  we add the sessionController class.
                        13.3  we add the sessionCreationPolicy() method in AppSecurityConfig class.    
            14. we are adding authentication filter in AppSecurityConfig class (read about from readme.md file)
                        14.1  we add the JwtAuthenticationFilter class which extends AuthenticationFilter class.
                                14.1.1  we have to add the parameterized constructor in JwtAuthenticationFilter class with two arguments.
                        14.2  we access the JwtAuthenticationFilter class in AppSecurityConfig class.
                                14.3  we need to create the bean for the JwtAuthenticationFilter class in AppSecurityConfig class.
                        14.3  we added .addFilterBefore() method in AppSecurityConfig class with two arguments. (must read it from AuthToken.md file)
            15. we added the authentication manager & authentication converter classes. 
                        15.1 we added the AuthenticationManager class which extends the AuthenticationManager class. 
                                15.1.1  we implements the authenticate() method in AuthenticationManager class.
                        15.2 we added the AuthenticationConverter class which extends the AuthenticationConverter class.
                                15.2.1  we implements the convert() method in AuthenticationConverter class.
            16. we are implements that "How we can read the Bearer token" in AuthenticationConverter class.
                          bearer token:- Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdGFyYyIsImlhdCI6MTY5MTAwNDY3NH0.5KOu66mbFKONL-vandG4nN_tigO5wQVGIid0O9ZZMEA
                        16.1 we get the token from the request header in convert() method of AuthenticationConverter class using request.getHeader("Authorization").
                        16.2 we take the token from the request header & we split it with "Bearer " & space, then take substring(7) from the bearer token.
                        16.3 we are checking the token is JWT or not || Server side token or not. whichever method we are using. 
                        16.4 we return the JWTAuthentication object with token.
            17. we are creating JWTAuthentication class  that will create JWTAuthentication object. (creating Authentication object with token)     
                        17.1 we create a jwtString field in JWTAuthentication class.
                        17.2 getCredentials() method in JWTAuthentication class will return the jwtString. 
            18. we need JWTService class object & UserSerivce class object in AuthenticationManager class. so we create the constructor in AuthenticationManager class.
                            in authenticate() method of AuthenticationManager class we get the JWTAuthentication object from the AuthenticationConverter class. (we finding that user exists or not from the Authentication Object.)
                        18.1   in Manager class we will check that instance of Authentication object is JWTAuthentication or not. 
                                18.1.1  by using jwtAuthentication.getCredentials() method we get the jwtString. means the token.
                        18.2   by using jwtService.getUserFromToken() method we get the username from the token. this step can be failed if token is not valid.
                        18.3   we get the user from the userService class using username.
                                18.3.1  for doing that we need to create getUserByUsername() method in UserRepo class.
                                18.3.2  we create getUserByUsername() method in UserService class. that returns the user
                        18.4   we get the user in the AuthenticationManager class. by using the userSerivce.getUserByUsername() method.
                        18.5   we chnage the return type of getPrincipal() method in JWTAuthentication class. (we return the User object called UserResponseDto)
                                18.5.1  we create the UserResponseDto object(user) in JWTAuthentication class. & setter method for that.
                                    (when authentication object is created (JWTAuthenticationConverter) it only contains jwt string. when it goes through the manager that time jwt string convert into user object. so rest of the application will deal with user object.)
                                18.5.2  for doing that manager class will set the user object in the JWTAuthentication class. by doing jwtAuthentication.setUser(user).
                                    (at converter class, we create the authentication object & in manager class we populate the authentication object with the user once its verfiied)
            19. we have changed the constructor argument in JWTAuthenticationFilter class where we have to just pass the AuthenticationManager object. (read the AuthToken.md file)
                        19.1    instead of using bean in AppSecurityConfig class we are createing the object of JWTAuthenticationFilter class in AppSecurityConfig class.
                        19.2    we have added the jwtservice & userservice in the constructor of AppSecurityConfig class. & we are passing the jwtservice & userservice object in the constructor of JWTAuthenticationFilter class.  & pass it to JWTAuthenticationManager class.      
            20. we have just Success handler in JwtAuthenticationFilter class. (read below).
                        20.1    we have added the successHandler() method in JwtAuthenticationFilter class.   
                      (now, how context is available everywhere in my code that we doing below)
            21. we have added @AuthenticationPrincipal in the privateAbout() of AboutController. (read below:- before 20).
                            (wherever we write @AuthenticationPrincipal annotation in controller we will get the authentication object.)
                        21.1    we are getting the user object in the privateAbout() method (test:- if client pass token then we get the user object otherwise we get the null).
            22. Testing:-  we are testing JwtService class.  (read readme.md file)
                        22.1    we need only JwtService class object in JwtServiceTest class. so we construct this only becoz we don't have any dependency so we don't need the dependency layer. 
                        22.2    we create this createJwt_works_with_username() in JwtServiceTest class. (read readme.md file)
                        22.3    we create this createJwt_throws_exception_when_token_is_null() in JwtServiceTest class. (read readme.md file)
                                22.3.1  we handle the IllegalArgumentException in createJwt() method of JwtService class. after testing 22.3 method, that is called TDD.
            23. Testing AuthToken layer
                        23.1    we create the package called com.kartik.backend_microservices.security.authtoken_server_side in test folder. 
                        23.2    create AuthTokenServiceTest class in this package.     
                                23.2.1  added the annotation with text called @DataJpaTest in AuthTokenServiceTest class.
                                23.2.2  we annotate the object of AuthTokenService class with @Autowired in AuthTokenServiceTest class.
                                23.2.3  write the test case called createAuthToken_works_with_userentity() in AuthTokenServiceTest class.
                                        (to create a token we need userEntity so if we want userEntity then it has to be actual userEntity that saved in userService. so we have to @Autowired the userService object in AuthTokenServiceTest class.)  
                                23.2.4  we @Autowired the userService object in AuthTokenServiceTest class. (while creating we added new annotation in CreateUserDto class.)
                                23.2.5  we create the object using createUser()
                        23.3    we need the userRepo object to get the UserEntity object that required in createToken(). (there are other ways also to get the userEntity)
                        23.4    we create the @BeforeEach to set up the serices that we injected in AuthTokenServiceTest class.
                                    
                        
                        
            
            
            
            
            
            
            
        Case Study:-
                    
                    when we access this url (http://localhost:8282/about/test) from browser then it will redirect to default login page.
                    & if we access through postman then it is saying unauthorized.
                       
                    Why this redirection is happening?
                        because we are using httpBasic() in configure method of AppSecurityConfig class.
                        where spring has its internal security mechanism where it takes username & password, & it makes it part of our cookie.
                        for that we have to write code to match the username & password.
                        rather than that we want token based authentication.
                        
                        this is sending the username & password in the Form data means without encryption.
            
        Session management:-
                            "it enforces only single instance of the user is authenticated at a time." 
                                means, whenever somebody is using any of the api endpoints spring will make sure that only one instance of the user is authenticated.   
                                let say, certain header if we want to read somebody passes two times the same header one with token of user1 & another with token of user2.
                                then spring will never treat an HTTP Request To have two different users. session management automatically takes care of that.
                                
                            How to do that ?
                                we create a sessionController. 
        
        Session Creation Policy:-
                            like we use Stateless, means SERVER will not in the memory, it is expect that session data coming alwasys from the client. which is HTTP Header.
                                     every request will come with http header.
            
            
            
            
            
        before 20:-
                    after setting up the JWTAuthenticationConverter & JWTAuthenticationManager class we are getting the user object in the AuthenticationManager class.
                    after user object we set the success handler in JWTAuthenticationFilter class.   
                    for setting the success handler we need security context  (read this https://www.baeldung.com/get-user-in-spring-security)
                    like this:-
                             SecurityContextHolder.getContext().setAuthentication(authentication);
                        what does this line do ?
                              when inside the JWTAutenticationFilter security authentication is successfull when JWTAuthenticationManager send non-null value
                              then success handler actually set the authentication object in the security context.
                          IMP:- "AS SOON AS AUTHENTICATION OBJECT GET SETS THEN ACROSS MY ENTIRE APPLICATION, EVERYWHERE INSIDE MY CONTROLLER THIS 
                                    AUTHENTICATION OBJECT IS AVAILABLE TO ME."
                             How this will help?
                                everywhere in my project in my controller after the request has passed to this filter, i would know "WHO IS AUTHENTICATED"
                                    becoz this authentication object is available to me. means inside the authentication object i have the user object.
                        what is context ?
                                read this from readme.md file        
            
                        in this below line:-
                            this.setSuccessHandler((request, response, authentication) -> {
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                            });
                            
                        here we are setting the authentication object in the security context we are setting up the authentication object 
                        whenever the authentication is successfull. so that after successfull we can reterive the user object from the security context
                        whenever we want.
            
                    Summary:-
                            In the security config, we added the authentication filter that authentication filter we have to create using 
                            jwtAuthenticationConverter & jwtAuthenticationManager in the JWTAuthenticationFilter constructor.
                            the converter is simple & manager we created using the jwtService & userService.
            
            
            Ques:- Where the JWTAuthentication Manager being called ?
            Ans :- We are not calling, it is just default configuration of springboot that "When we create a Filter (in AppSecurityConfig class) 
                    inside the constructor of the filter (JWTAuthenticationFilter) we are passing the authentication manager & authentication converter. 
                    so springboot will automatically do this (AuthenticationFilter.png) for us." 
                    if client pass the token then it will call the manager class. if convertor return the authentication object then it will call the authentication manager. 
                        if the manager failed to authenticate then it means that request is not authenticated. || if the convertor is failed that means the format is wrong. so it moves to the next filter.
             
                    -   when the filter is running (like apart from these about, login, signup) if we set the context then every controller that is behind that filter 
                        (means, all the api which need to authenticate) will have the authentication object. 
            
            Ques:-  In the Auth service layer test, whenever we are making the object of the repository like AuthTokenRepository. 
                     how AuthTokenRepository is constructed, during the test how we can actually mock this AuthTokenRepository ?
            Ans :-   point 23.
            
            
        @DataJpaTest:-  (It is faster)
                        We are testing the Repository layer. so we used @DataJpaTest annotation.
                        it will create the in-memory database. so that we can test the repository layer.   
                        here, we don't need to boot the entire application. we just need to boot the repository layer.
                        so, we don't need to use @SpringBootTest annotation (it will boot the MVC Layer). 
                        
                        unless we are not testing the controller layer we don't need to use @SpringBootTest annotation.
                        
                        if we use @DataJpaTest annotation then it will not access the Service layer so that if 
                        we access the service layer then it will throw the error.
                        so the services are not generated automatcially. only the repository layer is generated.
                            we will create the services in our test class. 
            

         
        @SpringBootTest:- (It is slower)                    
    
    
    
    
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
``` 

[Guide 1](https://spring.io/guides/gs/securing-web/):- to implement the JWT token in spring boot application.
[Guide 2](https://baeldung.com/get-user-in-spring-security):- to implement the JWT token in spring boot application.    both are same. 