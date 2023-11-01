package com.example.Library.repositories;

import com.example.Library.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<List<Customer>> findCustomersByName(String name);
    Optional<List<Customer>> findCustomersBySurname(String surname);
}
