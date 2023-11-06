package com.example.Library.services;

import com.example.Library.models.BookCustomer;
import com.example.Library.repositories.BookCustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookCustomerService {
    private final BookCustomerRepository bookCustomerRepository;

    @Autowired
    public BookCustomerService(BookCustomerRepository bookCustomerRepository1) {
        this.bookCustomerRepository = bookCustomerRepository1;
    }

    public LocalDateTime getTakenAtTime(Long bookId, Long customerId) {
        Optional<BookCustomer> bookCustomer = bookCustomerRepository.findByBookIdAndCustomerId(bookId, customerId);

        if (bookCustomer.isPresent()) {
            return bookCustomer.get().getTakenAt();
        } else {
            throw new EntityNotFoundException("Book not found for the given customer.");
        }
    }

    public BookCustomer findByBookIdAndCustomerId(Long bookId, Long customerId) {
        return bookCustomerRepository.findByBookIdAndCustomerId(bookId, customerId).orElseThrow(() -> new EntityNotFoundException("BookCustomer not found"));
    }
}
