package com.kartik.backend_microservices.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.cors().disable().csrf().disable()
                 .authorizeRequests()
                    .antMatchers("/about").permitAll()
                    .antMatchers("/*/**").authenticated() // all other requests would be authenticated
                 .and()
                 .httpBasic();
         super.configure(http);
    }

}

/*
          // in production, CORS ans CSRF should be enabled
            CSRF is Cross Site Request Forgery
               It is a type of attack where a malicious user sends a request to a website to perform an action on behalf of the user

            CORS is Cross-Origin Resource Sharing
                It is a mechanism that allows restricted resources on a web page to be requested from another domain outside the domain from which the first resource was served
                means from google.com frontend we can't request data from facebook.com backend

             .httpBasic()
                    means page requires the username and password
                    it is by default provided by spring security

 */


