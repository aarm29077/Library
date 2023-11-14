package com.example.Library.controller;

import com.example.Library.dto.AuthorDTORequest;
import com.example.Library.dto.AuthorDTOResponse;
import com.example.Library.models.Author;
import com.example.Library.models.Book;
import com.example.Library.services.AuthorService;
import com.example.Library.services.DTOConversionService;
import com.example.Library.util.customAnnotations.ValidString;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@RequestMapping("/authors")
@Validated
public class AuthorController {
    private final AuthorService authorService;
    private final DTOConversionService dtoConversionService;

    @Autowired
    public AuthorController(AuthorService authorService1, DTOConversionService dtoConversionService) {
        this.authorService = authorService1;
        this.dtoConversionService = dtoConversionService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAuthors() {
        Optional<List<Author>> authors = authorService.findAuthors();
        if (authors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                authors.get().stream().map(dtoConversionService::convertToAuthorDTOResponse).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorByAuthorId(@PathVariable Long id) {
        Optional<Author> byId = authorService.findById(id);
        if (byId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dtoConversionService.convertToAuthorDTOResponse(byId.get()), HttpStatus.OK);
    }

    @GetMapping("getByName/{name}")
    public ResponseEntity<?> getAuthorsByAuthorName(@PathVariable @Validated @ValidString(message = "The given author's name is not valid") @NotBlank(message = "The name should not be empty") @Size(min = 2, max = 30, message = "The author's name should be between 2 and 30 characters") String name) {
        Optional<List<Author>> byAuthorName = authorService.findByAuthorName(name);
        if (byAuthorName.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(byAuthorName.get().stream().map(dtoConversionService::convertToAuthorDTOResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("getBySurname/{surname}")
    public ResponseEntity<?> getAuthorsByAuthorSurname(@PathVariable @Validated @NotBlank(message = "The surname should not be empty") @ValidString(message = "The given author's surname is not valid") @Size(min = 2, max = 30, message = "The author's surname should be between 2 and 30 characters") String surname) {
        Optional<List<Author>> byAuthorSurname = authorService.findByAuthorSurname(surname);
        if (byAuthorSurname.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(byAuthorSurname.get().stream().map(dtoConversionService::convertToAuthorDTOResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/getByNameAndSurname")
    public ResponseEntity<?> getAuthorsByAuthorNameAndSurname(
            @RequestParam @Validated @ValidString(message = "The given author's name is not valid") @NotBlank(message = "The name should not be empty") @Size(min = 2, max = 30, message = "The author's name should be between 2 and 30 characters") String name,
            @RequestParam @Validated @NotBlank(message = "The surname should not be empty") @ValidString(message = "The given author's surname is not valid") @Size(min = 2, max = 30, message = "The author's surname should be between 2 and 30 characters") String surname
    ) {
        Optional<Author> byAuthorNameAndAuthorSurname = authorService.findByAuthorNameAndAuthorSurname(name, surname);
        if (byAuthorNameAndAuthorSurname.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dtoConversionService.convertToAuthorDTOResponse(byAuthorNameAndAuthorSurname.get()), HttpStatus.OK);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<?> getBooksByAuthorID(@PathVariable Long id) {
        Optional<List<Book>> booksByAuthorId = authorService.findBooksByAuthorId(id);
        if (booksByAuthorId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(booksByAuthorId.get().stream().map(dtoConversionService::convertToBookDTOResponseAllInfo).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("getByNationality/{nationality}")
    public ResponseEntity<?> getAuthorsByNationality(@PathVariable @Validated @NotBlank(message = "Please send nationality of this author") @ValidString(message = "The given author's nationality is not valid") @Size(min = 2, max = 30, message = "he author's nationality should be between 2 and 30 characters") String nationality) {
        Optional<List<Author>> byNationality = authorService.findByNationality(nationality);
        if (byNationality.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(byNationality.get().stream().map(dtoConversionService::convertToAuthorDTOResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}/nationality")
    public ResponseEntity<?> getNationalityByAuthorId(@PathVariable Long id) {

        return ResponseEntity.ok(authorService.findNationalityByAuthorId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAuthor(@RequestBody @Valid AuthorDTORequest authorDTORequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Author author = authorService.createAuthor(dtoConversionService.convertToAuthor(authorDTORequest));

        AuthorDTOResponse createdAuthor = dtoConversionService.convertToAuthorDTOResponse(author);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }
}
