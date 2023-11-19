package com.example.Library.Config;

import com.example.Library.Config.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers("/api/v1/auth/register").permitAll()
                                .requestMatchers("/api/v1/auth/register","/api/v1/auth/authenticate").permitAll()
                                .requestMatchers("/swagger-ui/**","/javainuse-openapi/**").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);//katarum e jwtAuthFilter-y UsernamePasswordAuthenticationFilter-ic araj

        return http.build();
    }
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        //                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
//        http
//                .csrf(AbstractHttpConfigurer::disable)
////                .formLogin(AbstractHttpConfigurer::disable)
////                .httpBasic(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(
//                        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
//                                .requestMatchers("/api/v1/auth/**").permitAll()
//                                .anyRequest()
//                                .authenticated()
//                )
//                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);//katarum e jwtAuthFilter-y UsernamePasswordAuthenticationFilter-ic araj
//
//        http.build();
//    }

}
