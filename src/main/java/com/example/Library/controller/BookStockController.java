package com.example.Library.controller;

import com.example.Library.models.Book;
import com.example.Library.models.BookStock;
import com.example.Library.services.BookService;
import com.example.Library.services.BookStockService;
import com.example.Library.util.customAnnotations.ValidString;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("bookStock")
@Validated
public class BookStockController {
    private final BookStockService bookStockService;

    @Autowired
    public BookStockController(BookStockService bookStockService) {
        this.bookStockService = bookStockService;
    }

    @GetMapping("/getById/{id}/currentQuantity")
    public ResponseEntity<?> getCurrentQuantityByBookId(@PathVariable Long id) {
        Optional<Integer> currentQuantityByBookId = bookStockService.findCurrentQuantityByBookId(id);
        if (currentQuantityByBookId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("The current quantity of book with id : " + id + " is : " + currentQuantityByBookId.get(), HttpStatus.OK);
    }


    @GetMapping("/getById/{bookId}/totalQuantity")
    public ResponseEntity<?> getTotalQuantityByBookId(@PathVariable Long bookId) {
        Optional<Integer> totalQuantityByBookId = bookStockService.findTotalQuantityByBookId(bookId);
        if (totalQuantityByBookId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("The total quantity of book with id : " + bookId + " is : " + totalQuantityByBookId.get(), HttpStatus.OK);
    }


    @PostMapping("/{bookId}/add")//stock
    public ResponseEntity<?> add(@PathVariable Long bookId, @RequestBody @Min(value = 1, message = "The minimum quantity is 1") int quantity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        BookStock bookStock = bookStockService.quantityManagement(bookId, quantity);

        return new ResponseEntity<>("The total quantity of book with id: " + bookId + " is: " + bookStock.getTotalQuantity(), HttpStatus.OK);
    }
}
