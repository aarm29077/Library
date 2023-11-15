package com.example.Library.config;

import com.example.Library.security.SecurityUserDetails;
import com.example.Library.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");//It is the header that contains the JWT token or bearer token
        final String jwt;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {//'Bearer ' tokeny petq e sksvi Bearer-ov
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);//Bearer u prabely 6
        username = jwtService.extractUsername(jwt);//todo extract username from JWT token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){//User is not authenticated yet
            SecurityUserDetails securityUserDetails = this.userDetailsService.loadByUsername(username);
        }
    }
}
