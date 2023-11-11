package com.example.Library.dto;

import com.example.Library.util.customAnnotations.ValidString;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthorDTOResponse {

    private Long id;

    @NotBlank(message = "The name should not be empty")
    @Size(min = 2, max = 30, message = "The author's name should be between 2 and 30 characters")
    @ValidString(message = "The given author's name is not valid")
    private String name;

    @NotBlank(message = "The surname should not be empty")
    @Size(min = 2, max = 30, message = "The author's surname should be between 2 and 30 characters")
    @ValidString(message = "The given author's surname is not valid")
    private String surname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
