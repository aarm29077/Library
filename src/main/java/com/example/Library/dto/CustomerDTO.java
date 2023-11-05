package com.example.Library.dto;

import com.example.Library.util.customAnnotations.AgeOver12;
import com.example.Library.util.customAnnotations.ValidString;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CustomerDTO {

    @Size(min = 2, max = 30, message = "The customer's name should be between 2 and 30 characters")
    @NotBlank(message = "The customer's name should not be empty")
    @ValidString(message = "The given customer's name is not valid")
    private String name;

    @Size(min = 2, max = 30, message = "The customer's surname should be between 2 and 30 characters")
    @NotBlank(message = "The customer's surname should not be empty")
    @ValidString(message = "The given customer's surname is not valid")
    private String surname;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @AgeOver12
    private Date dateOfBirth;

    @Email(message = "Please provide a valid email address")
    @ValidString(message = "The given customer's email is not valid")
    private String email;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
