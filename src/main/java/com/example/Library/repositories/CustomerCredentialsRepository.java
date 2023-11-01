package com.example.Library.repositories;

import com.example.Library.models.Customer;
import com.example.Library.models.CustomerCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerCredentialsRepository extends JpaRepository<CustomerCredentials, Long> {
    Optional<Customer> findCustomerByUsername(String username);
}
