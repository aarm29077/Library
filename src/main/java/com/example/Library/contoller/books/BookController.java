package com.example.Library.contoller.books;

import com.example.Library.dto.book.BookDTORequest;
import com.example.Library.models.books.Author;
import com.example.Library.models.books.Book;
import com.example.Library.service.books.AuthorService;
import com.example.Library.service.books.BookService;
import com.example.Library.service.conversion.DTOConversionService;
import com.example.Library.util.customAnnotations.ValidString;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Validated
@SecurityRequirement(name = "javainuseapi")
public class BookController {
    private final BookService bookService;
    private final DTOConversionService dtoConversionService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, DTOConversionService dtoConversionService, AuthorService authorService) {
        this.bookService = bookService;
        this.dtoConversionService = dtoConversionService;
        this.authorService = authorService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getBooks() {//ADMIN,USER
        Optional<List<Book>> allBook = bookService.findAllBook();
        if (allBook.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allBook.get().stream().map(dtoConversionService::convertToBookDTOResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}/fullInformation")//ADMIN,USER
    public ResponseEntity<?> getBookFullInformationById(@PathVariable Long id) {
        Optional<Book> bookById = bookService.findBookById(id);
        if (bookById.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dtoConversionService.convertToBookDTOResponseAllInfo(bookById.get()), HttpStatus.OK);
    }

    @GetMapping("/{id}")//ADMIN,USER
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        Optional<Book> bookById = bookService.findBookById(id);
        if (bookById.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dtoConversionService.convertToBookDTOResponse(bookById.get()), HttpStatus.OK);
    }

    @GetMapping("/getByTitle/{title}")//ADMIN,USER
    public ResponseEntity<?> getBooksByBookTitle(@PathVariable @Validated @Size(min = 2, max = 30, message = "The book's title should be between 2 and 30 characters") @NotBlank(message = "The book's title should not be empty") @ValidString(message = "The given title is not valid") String title) {
        Optional<List<Book>> booksByBookTitle = bookService.findBooksByBookTitle(title);
        if (booksByBookTitle.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(booksByBookTitle.get().stream().map(dtoConversionService::convertToBookDTOResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/getByIsbn/{isbn}")//ADMIN,USER
    public ResponseEntity<?> getBookByBookISBN(@PathVariable @Validated @ISBN(message = "It isn't ISBN format") @ValidString(message = "The given ISBN is not valid") @NotBlank(message = "The ISBN should not be empty") String isbn) {
        Optional<Book> bookByBookIsbn = bookService.findBookByBookIsbn(isbn);
        if (bookByBookIsbn.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dtoConversionService.convertToBookDTOResponse(bookByBookIsbn.get()), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}/authors")//ADMIN,USER
    public ResponseEntity<?> getAuthorsByBookId(@PathVariable Long id) {
        Optional<List<Author>> authorsByBookId = bookService.findAuthorsByBookId(id);
        if (authorsByBookId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(authorsByBookId.get().stream().map(dtoConversionService::convertToAuthorDTOResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/getByIsbn/{isbn}/authors")//ADMIN,USER
    public ResponseEntity<?> getAuthorsByBookIsbn(@PathVariable @Validated @ISBN(message = "It isn't ISBN format") @ValidString(message = "The given ISBN is not valid") @NotBlank(message = "The ISBN should not be empty") String isbn) {
        Optional<List<Author>> authorsByBookIsbn = bookService.findAuthorsByBookIsbn(isbn);
        if (authorsByBookIsbn.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(authorsByBookIsbn.get().stream().map(dtoConversionService::convertToAuthorDTOResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}/publicationDate")//ADMIN,USER
    public ResponseEntity<?> getPublicationDateByBookId(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findPublicationDateByBookId(id));
    }

    @GetMapping("/getByIsbn/{isbn}/publicationDate")//ADMIN,USER
    public ResponseEntity<?> getPublicationDateByBookIsbn(@PathVariable @Validated @ISBN(message = "It isn't ISBN format") @ValidString(message = "The given ISBN is not valid") @NotBlank(message = "The ISBN should not be empty") String isbn) {
        return ResponseEntity.ok(bookService.findPublicationDateByBookIsbn(isbn));
    }

    @PostMapping("/create")//ADMIN
    public ResponseEntity<?> create(@RequestBody @Valid BookDTORequest bookDTORequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Book bookToBeInserted = dtoConversionService.convertToBook(bookDTORequest);

        if (!bookService.createBook(bookToBeInserted)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Optional<Book> bookByBookIsbn = bookService.findBookByBookIsbn(bookDTORequest.getIsbn());
        if (bookByBookIsbn.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bookByBookIsbn.get().getId(), HttpStatus.CREATED);
    }

    @PostMapping("/{bookId}/connectToAuthors")//ADMIN
    public ResponseEntity<?> connectToAuthors(@PathVariable Long bookId, @RequestBody List<Long> authorIds, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        List<Author> authorsByIds = authorService.findAuthorsByIds(authorIds);

        for (Author author : authorsByIds) {
            if (!bookService.addAuthorToBook(bookId, author)) {
                return new ResponseEntity<>("The author with id: " + author.getId() + " is already assigned as author of book with id: " + bookId, HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>("Book with ID: " + bookId + " updated successfully", HttpStatus.OK);

    }


    @DeleteMapping("/deleteByIsbn/{isbn}")//ADMIN
    public ResponseEntity<String> removeBookByBookIsbn(@PathVariable @Validated @ISBN(message = "It isn't ISBN format") @ValidString(message = "The given ISBN is not valid") @NotBlank(message = "The ISBN should not be empty") String isbn) {
        boolean removed = bookService.deleteBookByBookIsbn(isbn);

        if (removed) {
            return new ResponseEntity<>("Book with ISBN: " + isbn + " removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book with ISBN: " + isbn + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteById/{id}")//ADMIN
    public ResponseEntity<String> removeBookByBookId(@PathVariable Long id) {
        boolean removed = bookService.deleteBookByBookId(id);

        if (removed) {
            return new ResponseEntity<>("Book with ID " + id + " removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
