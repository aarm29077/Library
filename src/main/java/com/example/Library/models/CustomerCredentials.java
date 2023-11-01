package com.example.Library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customer_credentials")
public class CustomerCredentials {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    @Size(min = 2, max = 30, message = "The username should be between 2 and 30 characters")
    @NotBlank(message = "The username should not be empty")
    private String username;

    @Column(name = "password", unique = true)
    @Size(min = 5, max = 15, message = "The password should be between 5 and 15 characters")
    @NotBlank(message = "The password should not be empty")
    private String password;

    @OneToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id", unique = true)
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
