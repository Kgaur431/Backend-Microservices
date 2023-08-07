

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

    
        we are doing    .antMatchers(HttpMethod.GET,"/about").permitAll() this then we are allowing the user to access the about page without authentication.
                        but like if we want to allow the user to access the page like /about/test ... etc. then we have to do something like this
                        .antMatchers(HttpMethod.GET,"/about/**").permitAll() then we are allowing the user to access the pages after about without authentication.
                        otherwise we have to do something like this .antMatchers(HttpMethod.GET,"/about/test").permitAll() for each page.
                        or it will ask for the authentication.


        from point 14 we are now implementing the JWT token instead of httpBasic() authentication.
            old code:-  
                        @Override
                        protected void configure(HttpSecurity http) throws Exception {
                             http.cors().disable().csrf().disable()
                                     .authorizeRequests()
                                        .antMatchers(HttpMethod.GET,"/about").permitAll()
                                        .antMatchers(HttpMethod.POST, "/user/register", "/user/login").permitAll()
                                        .antMatchers("/*/**").authenticated() // all other requests would be authenticated
                                     .and()
                                     .httpBasic();
                    
                             super.configure(http);
                        }
            new code:-
                            @Override
                            protected void configure(HttpSecurity http) throws Exception {
                                 http.cors().disable().csrf().disable()
                                         .authorizeRequests()
                                            .antMatchers(HttpMethod.GET,"/about").permitAll()
                                            .antMatchers(HttpMethod.POST, "/user/register", "/user/login").permitAll()
                                            .antMatchers("/*/**").authenticated() // all other requests would be authenticated
                                         .and()
                                         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                        
                                 super.configure(http);
                            }

                read about session management from projectworkflow.md
                 


        jwtAuthenticationFilter:-  we added this before the AnonymousAuthenticationFilter 
        AnonymousAuthenticationFilter.class :-  this will detects if there is no authentication object populated it with one.
                                                    so this is kind of fallback mechanism.
                                                        "if no authentication has taken place then it will treat as anonymous user."
            "we want to add our jwtaut... filter before anonymous filter. becoz our jwtaut... filter must run before anonymous filter."
             
            but we need to pass AuthenticationManager & AuthenticationConverter to the jwtaut... filter to create the jwtaut... filter object.
                we need to create AuthenticationManager & AuthenticationConverter. 
                
            setting up the authentication is not the daily taks that we do so need to remember how we are doing this, below:-
                  1.    created a AppSecurityConfig class.
                  2.    inside this class we need to add filter.
                  3.    when we want to add the filter then we can creater our own filter like JwtAuthenticationFilter class.
                  4.    to creating the JwtAuthenticationFilter class we need to pass the AuthenticationManager & AuthenticationConverter.
                  5.    so we need to create the AuthenticationManager & AuthenticationConverter.
               
               Think like this:-
                              when we work on new framework || new part of framework.
                              we should keep figuring out things like this:- 
                                    config class needs a filter, lets try to create a filter.
                                    what does it require to create a filter ? 
                                        it requires AuthenticationManager & AuthenticationConverter.
                                    so lets try to create AuthenticationManager & AuthenticationConverter.
                                       & find out what is the job of the Manager & Converter.
               
               job of Authentication Manager ?
                        it process the authentication request & returns the authentication object if the authentication is successful.
               
               job of Authentication Converter ?
                        it will converts the httpserveletrequest to the authentication of an particular type.
               
               In the convert method of the AuthenticationConverter class we have an http request & 
                  from this req we have to create an authentication object.      
                  so we can check that from the req what we can get ? || what we can read.           
                    request. 
                    like:-
                            request.getHeader("Authorization") // this will give us the token.   
                            
                    In the postman there is an option called Authorization where we can pass the token. that token we can get from the req.   
        
        AuthenticationFilter;-
                    there are two entity.
                        1.    AuthenticationManager     (write code to validate the token)    
                        2.    AuthenticationConverter   (write code to read header)
                        
                        there is an config class called as AppSecurityConfig class.
                   
                        by the way there can be multiple authentication filter in the application   
                        
                        assume many request are coming to the server. 
                        the config class is saying that for certain requests authentication is not required so that request will be passed through the config class. like permitAll(). 
                        but where we don't write permitAll(), in those cases the request will go through certain filters (we have created chain of filters
                         like .addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);)
                          here, by default there is an AnonymousAuthenticationFilter.class filter which is end of the filter chain.
                                so before the chain we have added our filter called as jwtAuthenticationFilter or we can add more filters if we want before the AnonymousAuthenticationFilter.class filter. 
                          there is one such request going through the filter chain. that request will sent to filter chain.
                            in the filter chain what happen is ?
                                step 1:- request will go into the converter 
                                          In the converter, it will try to detect "Type of authentication" from the request. like header or something  that exist or not.
                                             {assume we have two filters in the filter chain before the AnonymousAuthenticationFilter.class filter.
                                                one is fot finding the jwt token & another is for finding the server side token.}
                                          
                                          so "AuthenticationConverter will try to create an Authentication object which AuthenticationConverter will send it to the AuthenticationManager."
                                          the AuthenticationManager will find out from there "Who the authentication principle is || who the user is ? || who is the authenticated user ?"
                                          
                                          what happen if authenticationCovnerter (first filter) had find out that header does not exists || cookie does not exists ? || whatever type of authentication we are trying to that does not exists ?
                                              then it would simply passed through the next filter (second filter) of the filter chain (red color line) 
                                              
                                          what happen when the token exists but the token is not the valid token or expiried token (problem occurs at AuthenticationManager) ?
                                                In those cases, AuthenticationManager will throw the 401 exception.
                                           
                                          that's why these two steps are seprate. 
                                            so AuthenticationConverter just checks the header which it suppose to carry weather the header exists or not.
                                               if the header exists (then we know we will going forward with the current AuthenticationFilter) so it will pass the header to the AuthenticationManager.
                                                then AuthenticationManager will check the token is valid or not.
                                               if the AuthenticationConverter fails then it will pass the request to the next filter like next AuthenticationFilter. and so on. 
                        
                                            suppose if the request pass from first AuthenticationFilter then it will go to the controller which is mapped to the request.
        
        
        
        Case Study:-                            before step 19 we are doing this stuff (think like this)
        
                Old Code:-  JWTAuthenticationFilter
                    public class JWTAuthenticationFilter extends AuthenticationFilter {
                        public JWTAuthenticationFilter(
                        AuthenticationManager authenticationManager,
                        AuthenticationConverter authenticationConverter) {
                            super(authenticationManager, authenticationConverter);
                        } 
                     }
                     
                New Code:-  JWTAuthenticationFilter
                    public class JWTAuthenticationFilter extends AuthenticationFilter {
                        public JWTAuthenticationFilter(
                            super(authenticationManager, new JwtAuthenticationConverter());
                        }
                    }
                
                these are to create the JWTAuthenticationFilter object.
                 to create JWTAuthenticationManager in AppSecurityConfig class we need the JWTService & UserService. 
                  to create JWTAuthenticationFilter we need JWTAuthenticationManager & JwtAuthenticationConverter
                  that we are doing in above old code.
                  we are keeping minimum code that requires in the constructor of the JwtAuthenticationFilter so we write new code (above),     **********imp********** always keep minimum things in constructor.
                  so jwtauthenticationcoverter we will create like new JwtAuthenticationConverter() in the constructor of the JwtAuthenticationFilter class 
                    (so JWTAuthenticationFilter constructor don't need to take the AuthenticationConverter as a parameter)
                  can we create a JwtAuthenticationManager also ?
                    No, becoz JwtAuthenticationManager needs the JWTService & UserService to create the JwtAuthenticationManager object.
                    so we can't do it, therefore we take the JwtAuthenticationManager as a parameter in the constructor of the JwtAuthenticationFilter class.   ********************that's how we have to deal with Dependency Injection.********************
                        
                            "THINGS THAT CAN BE DEPENDENCY INJECTED THEN WE DON'T NEED TO PUT IN ARGUMENTS like AuthenticationConverterW"
                            "THINGS THAT CAN'T BE DEPENDENCY INJECTED THEN WE HAVE TO GO ONE LEVEL ABOVE & CHECK AT WHICH LEVEL WE CAN 
                             INJECT, THAT NEED TO PUT IN ARGUMENTS like AuthenticationManager"
                    
                    inside the JwtAuthenticationFilter bean in the AppSecurityConfig class 
                    we can inject the jwtservice & userservice in the JwtAuthenticationManager bean  (this is one level above of the JWTAuthenticationFilter class)
                    so that we can create the object of JwtAuthenticationManager 
                    
                    
                  What we are doing is ?
                    we need to create jwtauthenticationfilter so to create jwtauthenticationfilter we need jwtauthenticationmanager & jwtauthenticationconverter.
                    now we have seen that the converter "HAS EMPTY CONSTRUCTOR" if its an empty construcor then we can create the object of the converter in the constructor of the jwtauthenticationfilter.
                    means we don't need to bring the ..converter inside the constructor parameter of the jwtauthenticationfilter. 
                    but the manager we can't build it inside the constructor of the jwtauthenticationfilter becoz it needs the jwtservice & userservice.
                    so the manager can be only build at a place where jwt service & user service is available.
                    now in the appsecurityconfig class basically a place "WHERE DEPENDENCY INJECTION DOES TAKE PLACE" (due to @Configuration annotation)
                    so inside the appsecurityconfig class we can get the access of the jwtservice & userservice. & we can dependency inject them where we want to. 
                  MOST-IMP:-
                        new way:-
                                we are making the jwtauthenticationfilter variable in the appsecurityconfig class.
                                & making the constructor of the AppSecurityConfig class & injecting the jwtservice & userservice in the constructor of the AppSecurityConfig class.
                                "Because AppSecurityConfig class is an @Configuration class (it is part of the dependency injection chain) so JWTService & Userservice we can inject anywhere
                                 because these services can be anotated with @Service, so it can created or injected anywhere (that ANYWHERE ITSELF MUST BE PLACE WHERE DEPENDNECY INJECTION DOES TAKE PLACE)
                                 like JwtAuthenticationFilter class is not the place where dependency injection does take place. so we can't inject the JWTService & UserService in the JwtAuthenticationFilter class. 
                                 becoz JwtAuthenticationFilter does not have any annotation like @Service or @Component or @Configuration that says that this class comes under in dependency injection area 
                                 but AppSecurityConfig class has @Configuration annotation so it comes under dependency injection area. so we can inject the JWTService & UserService in the AppSecurityConfig class."
                    
                    
                    
                    
                    old code:-      (this way we can do it)
                            @Configuration
                            public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
                            
                                @Bean
                                public JWTAuthenticationFilter jwtAuthenticationFilter(
                                         JWTSerivce jwtSerivce;
                                         UserService userService;
                                ) {
                                    return new JWTAuthenticationFilter(
                                            new JWTAuthenticationManger(),
                                            new JWTAuthenticationConverter()
                            
                                    );
                                }
                    
                    new code:-  
                            @Configuration
                            public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
                                private JWTAuthenticationFilter jwtAuthenticationFilter;
                            
                                public AppSecurityConfig(
                                        JwtService jwtService,
                                        UserService userService
                                ) {
                                    jwtAuthenticationFilter = new JWTAuthenticationFilter(
                                            new JWTAuthenticationManger(jwtService, userService)
                                    );
                                }
                     
                     here we are just make a private instance of the JWTAuthenticationFilter class & we are creating the object of the JWTAuthenticationFilter class in the constructor of the AppSecurityConfig class. 
                       while creating we are creating the object of the JWTAuthenticationManager class also.
                       & JwtAuthenticationManager class needs the JWTService & UserService to create the object of the JwtAuthenticationManager class.
                       that we are getting from the constructor of the AppSecurityConfig class.
           
```
    
