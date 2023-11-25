package com.example.Library.service.users;

import com.example.Library.models.users.User;
import com.example.Library.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserCredentialsService userCredentialsService;

    @Autowired
    public UserDetailsServiceImpl(UserCredentialsService userCredentialsService) {
        this.userCredentialsService = userCredentialsService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userCredentialsService.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return new UserDetailsImpl(user);
    }

}
