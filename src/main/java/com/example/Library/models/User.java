package com.example.Library.models;

import com.example.Library.util.customAnnotations.AgeOver12;
import com.example.Library.util.customAnnotations.ValidString;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "The user's name should be between 2 and 30 characters")
    @NotBlank(message = "The user's name should not be empty")
    @ValidString(message = "The given user's name is not valid")
    private String name;

    @Column(name = "surname")
    @Size(min = 2, max = 30, message = "The user's surname should be between 2 and 30 characters")
    @NotBlank(message = "The user's surname should not be empty")
    @ValidString(message = "The given user's surname is not valid")
    private String surname;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @AgeOver12
    private Date dateOfBirth;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Email(message = "Please provide a valid email address")
    @ValidString(message = "The given user's email is not valid")
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookUser> books;

    @OneToOne(mappedBy = "user",orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.PERSIST})
    private UserCredentials credentials;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BookUser> getBooks() {
        return books;
    }

    public void setBooks(List<BookUser> books) {
        this.books = books;
    }

    public UserCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(UserCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User customer = (User) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, dateOfBirth, registeredAt, email, credentials);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", registeredAt=" + registeredAt +
                ", email='" + email + '\'' +
                ", books=" + books +
                '}';
    }
}
