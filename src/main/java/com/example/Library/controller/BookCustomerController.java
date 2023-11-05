package com.example.Library.controller;

import com.example.Library.services.BookCustomerService;
import com.example.Library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/bookCustomer")
public class BookCustomerController {
    private final BookCustomerService bookCustomerService;

    @Autowired
    public BookCustomerController(BookCustomerService bookCustomerService) {
        this.bookCustomerService = bookCustomerService;
    }
    @GetMapping("/customers/{customerId}/books/{bookId}/taken-at")
    public ResponseEntity<LocalDateTime> getTakenAtTime(
            @PathVariable Long customerId,
            @PathVariable Long bookId
    ) {
        LocalDateTime takenAt = bookCustomerService.getTakenAtTime(bookId, customerId);
        return ResponseEntity.ok(takenAt);
    }
}
