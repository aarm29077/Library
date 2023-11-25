package com.example.Library.config;

import com.example.Library.config.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider, LogoutHandler logoutHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                                .requestMatchers(HttpMethod.GET, "/bookStock/{id}/stockInformation","/bookStock/getById/{id}/currentQuantity",
                                        "/bookStock/getById/{bookId}/totalQuantity","/users/all", "/users/getByName/{name}", "/users/getBySurname/{surname}",
                                        "/users/getByEmail/{email}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/books/deleteByIsbn/{isbn}", "books/deleteById/{id}", "/users/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/authors/create", "/books/create", "/books/{bookId}/connectToAuthors", "/bookStock/{bookId}/add").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.PUT, "/users/{id}").hasRole("USER")
                                .requestMatchers(HttpMethod.POST, "/{userId}/add-book/{bookId}", "/users//{userId}/release-book/{bookId}").hasRole("USER")

                                .requestMatchers("/api/v1/auth/register", "/api/v1/auth/authenticate", "/swagger-ui/**", "/javainuse-openapi/**").permitAll()

                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)//katarum e jwtAuthFilter-y UsernamePasswordAuthenticationFilter-ic araj
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) -> SecurityContextHolder.clearContext()));


        return http.build();
    }

}
