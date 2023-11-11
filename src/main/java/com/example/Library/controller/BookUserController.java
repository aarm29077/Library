package com.example.Library.controller;

import com.example.Library.services.BookUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/bookUser")
public class BookUserController {
    private final BookUserService bookUserService;

    @Autowired
    public BookUserController(BookUserService bookUserService) {
        this.bookUserService = bookUserService;
    }

    @GetMapping("/users/{userId}/books/{bookId}/taken-at")
    public ResponseEntity<LocalDateTime> getTakenAtTime(
            @PathVariable Long userId,
            @PathVariable Long bookId
    ) {
        LocalDateTime takenAt = bookUserService.getTakenAtTime(bookId, userId);
        return ResponseEntity.ok(takenAt);
    }
}
