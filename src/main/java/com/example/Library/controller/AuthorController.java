package com.example.Library.controller;

import com.example.Library.dto.AuthorDTOResponse;
import com.example.Library.dto.BookDTOResponse;
import com.example.Library.services.AuthorService;
import com.example.Library.services.DTOConversionService;
import com.example.Library.util.customAnnotations.ValidString;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<AuthorDTOResponse> getAuthors() {
        return authorService.findAuthors().stream().map(dtoConversionService::convertToAuthorDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AuthorDTOResponse getAuthorByAuthorId(@PathVariable Long id) {
        return dtoConversionService.convertToAuthorDTOResponse(authorService.findById(id));
    }

    @GetMapping("getByName/{name}")
    public List<AuthorDTOResponse> getAuthorsByAuthorName(@PathVariable @Validated @ValidString(message = "The given author's name is not valid") @NotBlank(message = "The name should not be empty") @Size(min = 2, max = 30, message = "The author's name should be between 2 and 30 characters") String name) {
        return authorService.findByAuthorName(name).stream().map(dtoConversionService::convertToAuthorDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("getBySurname/{surname}")
    public List<AuthorDTOResponse> getAuthorsByAuthorSurname(@PathVariable @Validated @NotBlank(message = "The surname should not be empty") @ValidString(message = "The given author's surname is not valid") @Size(min = 2, max = 30, message = "The author's surname should be between 2 and 30 characters") String surname) {
        return authorService.findByAuthorSurname(surname).stream().map(dtoConversionService::convertToAuthorDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/getByNameAndSurname")
    public List<AuthorDTOResponse> getAuthorsByAuthorNameAndSurname(
            @RequestParam @Validated @ValidString(message = "The given author's name is not valid") @NotBlank(message = "The name should not be empty") @Size(min = 2, max = 30, message = "The author's name should be between 2 and 30 characters") String name,
            @RequestParam @Validated @NotBlank(message = "The surname should not be empty") @ValidString(message = "The given author's surname is not valid") @Size(min = 2, max = 30, message = "The author's surname should be between 2 and 30 characters") String surname
    ) {
        return authorService.findByAuthorNameAndAuthorSurname(name, surname).stream().map(dtoConversionService::convertToAuthorDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}/books")
    public List<BookDTOResponse> getBooksByAuthorID(@PathVariable Long id) {
        return authorService.findBooksByAuthorId(id).stream().map(dtoConversionService::convertToBookDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("getByNationality/{nationality}")
    public List<AuthorDTOResponse> getAuthorsByNationality(@PathVariable @Validated @NotBlank(message = "Please send nationality of this author") @ValidString(message = "The given author's nationality is not valid") @Size(min = 2, max = 30, message = "he author's nationality should be between 2 and 30 characters") String nationality) {
        return authorService.findByNationality(nationality).stream().map(dtoConversionService::convertToAuthorDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}/nationality")
    public String getNationalityByAuthorId(@PathVariable Long id) {
        return authorService.findNationalityByAuthorId(id);
    }

}
