package com.kartik.backend_microservices.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                    .antMatchers(HttpMethod.GET,"/about").permitAll()
                    .antMatchers(HttpMethod.POST, "/user/register", "/user/login").permitAll()
                    .antMatchers("/*/**").authenticated() // all other requests would be authenticated
                 .and()
                 .httpBasic();
         super.configure(http);
    }

}




