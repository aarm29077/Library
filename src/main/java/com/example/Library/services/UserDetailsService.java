//package com.example.Library.services;
//
//
//import com.example.Library.models.Customer;
//import com.example.Library.repositories.CustomerCredentialsRepository;
//import com.example.Library.security.CustomerDetails;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional(readOnly = true)
//public class CustomerDetailsService implements UserDetailsService {
//    private final CustomerCredentialsRepository customerCredentialsRepository;
//
//    public CustomerDetailsService(CustomerCredentialsRepository customerCredentialsRepository) {
//        this.customerCredentialsRepository = customerCredentialsRepository;
//    }
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Customer customer = customerCredentialsRepository.findCustomerByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        return new CustomerDetails(customer);
//    }
//
//}
