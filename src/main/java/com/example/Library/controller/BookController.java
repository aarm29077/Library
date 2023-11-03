package com.example.Library.controller;

import com.example.Library.dto.AuthorDTOResponse;
import com.example.Library.dto.BookDTORequest;
import com.example.Library.dto.BookDTOResponse;
import com.example.Library.services.BookService;
import com.example.Library.services.DTOConversionService;
import com.example.Library.util.customAnnotations.ValidString;
import com.example.Library.util.customExceptions.BookNotCreatedException;
import jakarta.validation.Valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {
    private final BookService bookService;
    private final DTOConversionService dtoConversionService;

    @Autowired
    public BookController(BookService bookService, DTOConversionService dtoConversionService) {
        this.bookService = bookService;
        this.dtoConversionService = dtoConversionService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> create(@RequestBody @Valid BookDTORequest bookDTORequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new BookNotCreatedException(errorMsg.toString());
        }
        boolean inserted = bookService.addBook(dtoConversionService.convertToBook(bookDTORequest));

        if (inserted) {
            return new ResponseEntity<>("Book with ISBN " + bookDTORequest.getIsbn() + " is inserted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book with ISBN " + bookDTORequest.getIsbn() + " already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public List<BookDTOResponse> getBooks() {
        return bookService.findAllBook().stream().map(dtoConversionService::convertToBookDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDTOResponse getBookById(@PathVariable Long id) {
        return dtoConversionService.convertToBookDTOResponse(bookService.findBookById(id));
    }

    @GetMapping("/{title}")
    public List<BookDTOResponse> getBooksByBookTitle(@PathVariable @Validated @Size(min = 2, max = 30, message = "The book's title should be between 2 and 30 characters") @NotBlank(message = "The book's title should not be empty") @ValidString(message = "The given title is not valid") String title) {
        return bookService.findBooksByBookTitle(title).stream().map(dtoConversionService::convertToBookDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/{isbn}")
    public List<BookDTOResponse> getBooksByBookISBN(@PathVariable @Validated @ISBN(message = "It isn't ISBN format") @ValidString(message = "The given ISBN is not valid") @NotBlank(message = "The ISBN should not be empty") String isbn) {
        return bookService.findBooksByBookIsbn(isbn).stream().map(dtoConversionService::convertToBookDTOResponse).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeBookByBookId(@PathVariable Long id) {
        boolean removed = bookService.deleteBookByBookId(id);

        if (removed) {
            return new ResponseEntity<>("Book with ID " + id + " removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> removeBookByBookIsbn(@PathVariable @Validated @ISBN(message = "It isn't ISBN format") @ValidString(message = "The given ISBN is not valid") @NotBlank(message = "The ISBN should not be empty") String isbn) {
        boolean removed = bookService.deleteBookByBookIsbn(isbn);

        if (removed) {
            return new ResponseEntity<>("Book with ISBN: " + isbn + " removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book with ISBN: " + isbn + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/authors")
    public List<AuthorDTOResponse> getAuthorsByBookId(@PathVariable Long id) {
        return bookService.findAuthorsByBookId(id).stream().map(dtoConversionService::convertToAuthorDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/{isbn}/authors")
    public List<AuthorDTOResponse> getAuthorsByBookIsbn(@PathVariable @Validated @ISBN(message = "It isn't ISBN format") @ValidString(message = "The given ISBN is not valid") @NotBlank(message = "The ISBN should not be empty") String isbn) {
        return bookService.findAuthorsByBookIsbn(isbn).stream().map(dtoConversionService::convertToAuthorDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}/publicationDate")
    public Date getPublicationDateByBookId(@PathVariable Long id) {
        return bookService.findPublicationDateByBookId(id);
    }

    @GetMapping("/{isbn}/publicationDate")
    public Date getPublicationDateByBookIsbn(@PathVariable @Validated @ISBN(message = "It isn't ISBN format") @ValidString(message = "The given ISBN is not valid") @NotBlank(message = "The ISBN should not be empty") String isbn) {
        return bookService.findPublicationDateByBookIsbn(isbn);
    }

    @GetMapping("/{id}/currentQuantity")
    public Integer getCurrentQuantityByBookId(@PathVariable Long id) {
        return bookService.findCurrentQuantityByBookId(id);
    }

    @GetMapping("/{isbn}/currentQuantity")
    public Integer getCurrentQuantityByBookIsbn(@PathVariable @Validated @ISBN(message = "It isn't ISBN format") @ValidString(message = "The given ISBN is not valid") @NotBlank(message = "The ISBN should not be empty") String isbn) {
        return bookService.findCurrentQuantityByBookIsbn(isbn);
    }

    @GetMapping("/{id}/totalQuantity")
    public Integer getTotalQuantityByBookId(@PathVariable Long id) {
        return bookService.findTotalQuantityByBookId(id);
    }

    @GetMapping("/{isbn}/totalQuantity")
    public Integer getTotalQuantityByBookIsbn(@PathVariable @Validated @ISBN(message = "It isn't ISBN format") @ValidString(message = "The given ISBN is not valid") @NotBlank(message = "The ISBN should not be empty") String isbn) {
        return bookService.findTotalQuantityByBookIsbn(isbn);
    }

}
