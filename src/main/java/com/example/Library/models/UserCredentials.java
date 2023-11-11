package com.example.Library.models;

import com.example.Library.util.customAnnotations.ValidString;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "user_credentials")
public class UserCredentials {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    @Size(min = 2, max = 30, message = "The username should be between 2 and 30 characters")
    @NotBlank(message = "The username should not be empty")
    @ValidString(message = "The given string is not valid")
    private String username;

    @Column(name = "password", unique = true)
    @Size(min = 5, max = 15, message = "The password should be between 5 and 15 characters")
    @NotBlank(message = "The password should not be empty")
    @ValidString(message = "The given string is not valid")
    private String password;

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCredentials that = (UserCredentials) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, user);
    }
}
