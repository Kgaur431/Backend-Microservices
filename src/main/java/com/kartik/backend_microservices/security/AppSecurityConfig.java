package com.kartik.backend_microservices.security;


import com.kartik.backend_microservices.security.jwt.JWTAuthenticationConverter;
import com.kartik.backend_microservices.security.jwt.JWTAuthenticationFilter;
import com.kartik.backend_microservices.security.jwt.JWTAuthenticationManger;
import com.kartik.backend_microservices.security.jwt.JwtService;
import com.kartik.backend_microservices.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    public JWTAuthenticationFilter jwtAuthenticationFilter() {
//        return new JWTAuthenticationFilter(
//                new JWTAuthenticationManger(),
//                new JWTAuthenticationConverter()
//
//        );
//    }

    private JWTAuthenticationFilter jwtAuthenticationFilter;

    public AppSecurityConfig(
            JwtService jwtService,
            UserService userService
    ) {
        jwtAuthenticationFilter = new JWTAuthenticationFilter(
                new JWTAuthenticationManger(jwtService, userService)
        );
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.cors().disable().csrf().disable()
                 .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/about").permitAll()
                    .antMatchers(HttpMethod.POST, "/user/register", "/user/login").permitAll()
                    .antMatchers("/*/**").authenticated() // all other requests would be authenticated
                 .and()
                 .addFilterBefore(jwtAuthenticationFilter , AnonymousAuthenticationFilter.class)
                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         super.configure(http);
    }

}


/**
 *      TODO:- WE CAN ENABLE BOTH TYPE OF AUTHENTICATION.
 *  here, we can server side Authentication filter with auth token.
 *      1. create SSTAuthenticationFilter in AppSecurityConfig class.
 *      2. add this .addFilterBefore(SSTAuthenticationFilter , AnonymousAuthenticationFilter.class) in configure method.
 *      3. In Manager class, we have to use AuthTokenService to find out to whom this token belongs to.
 *      4. which type of authentication are we doing?
 *          In the Controller we will add @RequestParam("authTokentype") String authToken like when someone is trying to login.
 *
 */




