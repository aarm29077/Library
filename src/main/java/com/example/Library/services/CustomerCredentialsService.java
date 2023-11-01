package com.example.Library.services;


import com.example.Library.models.Customer;
import com.example.Library.repositories.CustomerCredentialsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerCredentialsService implements UserDetailsService {
    private final CustomerCredentialsRepository customerCredentialsRepository;

    public CustomerCredentialsService(CustomerCredentialsRepository customerCredentialsRepository) {
        this.customerCredentialsRepository = customerCredentialsRepository;
    }

    public Customer getCustomerByUsername(String username) {
        return customerCredentialsRepository.findCustomerByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
