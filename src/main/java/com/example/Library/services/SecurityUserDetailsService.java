//package com.example.Library.services;
//
//import com.example.Library.models.User;
//import com.example.Library.models.UserCredentials;
//import com.example.Library.repositories.UserCredentialsRepository;
//import com.example.Library.security.SecurityUserDetails;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class SecurityUserDetailsService implements UserDetailsService {
//
//    private final UserCredentialsRepository userCredentialsRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserCredentials userCredentials = userCredentialsRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        User user = userCredentials.getUser();
//
//        return new SecurityUserDetails(new SecurityUserDetails(user));
//    }
//
//}
