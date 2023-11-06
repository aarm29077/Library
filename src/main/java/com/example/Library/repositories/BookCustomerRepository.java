package com.example.Library.repositories;

import com.example.Library.models.Book;
import com.example.Library.models.BookCustomer;
import com.example.Library.models.BookCustomerId;
import com.example.Library.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookCustomerRepository extends JpaRepository<BookCustomer, BookCustomerId> {
//    Optional<BookCustomer> findByBookAndCustomer(Book book, Customer customer);

    Optional<BookCustomer> findByBookIdAndCustomerId(Long bookId, Long customerId);
}
