package com.example.Library.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    @Override//stex klini Principal(CustomerDetails)  //stex credentials
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

  
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
