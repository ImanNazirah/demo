package com.example.demo.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {
@Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/**").permitAll();
    }
        
}