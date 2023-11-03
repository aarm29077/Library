package com.example.Library.config;

import com.example.Library.services.CustomerDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SpringConfig {

    private final CustomerDetailsService customerDetailsService;

    public SpringConfig(CustomerDetailsService customerDetailsService1) {
        this.customerDetailsService = customerDetailsService1;
    }

    protected void configure(HttpSecurity http) throws Exception {
        //confugure Spring Security
        //congigure authorization

    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerDetailsService);
    }

    @Bean//passwordneri encode-i hamar
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
