package com.example.Library.Config.filter;

import com.example.Library.Services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        /*if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }*/
        /*
        It is the header that contains the JWT token or bearer token*/
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        /*
        'Bearer ' tokeny petq e sksvi Bearer-ov*/
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);//Bearer u space-y 6
        username = jwtService.extractUsername(jwt);//todo extract username from JWT token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {//User is not authenticated yet
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);//db-ic vercnum enq userin
            /*
            You're passing null as the second argument, which represents the "credentials"
            in the context of a UsernamePasswordAuthenticationToken. This is acceptable and
            common in scenarios where the authentication is not based on a traditional
            username/password setup. In token-based authentication (like JWT authentication),
            the token itself is used as a form of credential.

            In token-based authentication:

            -The userDetails typically represents information about the authenticated user,
            such as the username, authorities, etc.
            -The null passed as the "credentials" in this context signifies that no explicit
            password is associated with the authentication token. This is because token-based
            authentication relies on the validity and integrity of the token itself, not a
            password.

            So, in summary, it's a valid approach not to include explicit credentials in the
            UsernamePasswordAuthenticationToken when dealing with token-based authentication.
            The token itself serves as the credential for the authentication process.
            */

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(//in order to update our SecurityContext
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                /*
                1)We extend or enforce this authentication token with the details of our
                request and then we update the authentication token
                */
                /*
                2)The line is essentially attaching web-specific details from the current
                HttpServletRequest to the Authentication object (authToken). This can be useful
                for logging or auditing purposes, as it provides additional context about the
                authentication request, such as the IP address, session ID, etc.

                Here's a common use case: If you later log authentication events, the details
                set here may be included in the log entries to provide more information about
                where the authentication request came from. This can be helpful for security and
                debugging purposes.
                */
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
            filterChain.doFilter(request, response);
    }
}
