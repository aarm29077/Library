package com.example.Library.config;

import com.example.Library.models.forUsers.User;
import com.example.Library.security.UserDetailsImpl;
import com.example.Library.service.forUsers.UserCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    private final UserCredentialsService service;

    @Autowired
    public ApplicationConfig(UserCredentialsService service) {
        this.service = service;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            User user = service.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return new UserDetailsImpl(user);
        };
    }
    /*
    AuthenticationProvider is the data access object which is responsible to fetch the user
    details and also encode password and so. And so for this we have many implementations and
    one of them is DAOAuthenticationProvider
    */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /*
    AuthenticationManager and AuthenticationProvider Relationship:

    The AuthenticationManager delegates the authentication process to one or more AuthenticationProvider instances.
    During the authentication process, the AuthenticationManager iterates over its configured AuthenticationProvider instances,
    calling the authenticate method on each until one of them successfully authenticates the user or all providers have been exhausted.
    If none of the providers can authenticate the user, the AuthenticationManager throws an AuthenticationException.
    Spring Security's default implementation of AuthenticationManager is ProviderManager, which manages a list of AuthenticationProvider instances.
    */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
