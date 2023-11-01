package com.example.Library.models;

import com.example.Library.util.AgeOver12;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "The customer's name should be between 2 and 30 characters")
    @NotBlank(message = "The customer's name should not be empty")
    private String name;

    @Column(name = "surname")
    @Size(min = 2, max = 30, message = "The customer's surname should be between 2 and 30 characters")
    @NotBlank(message = "The customer's surname should not be empty")
    private String surname;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @AgeOver12
    private Date dateOfBirth;

    @Column(name = "registered_at")
    @NotBlank
    private LocalDateTime registeredAt;

    @ManyToMany(mappedBy = "customers")
    private Set<Book> books;

    @OneToOne(mappedBy = "customer")
    @Cascade({org.hibernate.annotations.CascadeType.PERSIST})
    private CustomerCredentials credentials;

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

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public CustomerCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(CustomerCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(surname, customer.surname) && Objects.equals(dateOfBirth, customer.dateOfBirth) && Objects.equals(registeredAt, customer.registeredAt) && Objects.equals(credentials, customer.credentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, dateOfBirth, registeredAt, credentials);
    }
}
