package com.example.Library.models.users;

import com.example.Library.models.BookUser;
import com.example.Library.models.roles.Role;
import com.example.Library.util.customAnnotations.AgeOver12;
import com.example.Library.util.customAnnotations.ValidString;
import com.example.Library.token.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Builder
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long id;

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "The user's name should be between 2 and 30 characters")
    @NotBlank(message = "The user's name should not be empty")
    @ValidString(message = "The given user's name is not valid")
    private @Getter @Setter String name;

    @Column(name = "surname")
    @Size(min = 2, max = 30, message = "The user's surname should be between 2 and 30 characters")
    @NotBlank(message = "The user's surname should not be empty")
    @ValidString(message = "The given user's surname is not valid")
    private @Getter @Setter String surname;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @AgeOver12
    private @Getter @Setter Date dateOfBirth;

    @Column(name = "registered_at",nullable = false)
    private LocalDateTime registeredAt;

    @Email(message = "Please provide a valid email address")
    @ValidString(message = "The given user's email is not valid")
    private @Getter @Setter String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookUser> books ;

    @OneToOne(mappedBy = "user",orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private @Getter @Setter UserCredentials credentials;

    @ManyToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private @Getter @Setter Role role;

    @OneToMany(mappedBy = "user")
    private @Getter @Setter List<Token> tokens;

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }


    public List<BookUser> getBooks() {
        return books;
    }

    public void setBooks(List<BookUser> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(dateOfBirth, user.dateOfBirth) && Objects.equals(registeredAt, user.registeredAt) && Objects.equals(email, user.email) && Objects.equals(credentials, user.credentials) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, dateOfBirth, registeredAt, email, credentials, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", registeredAt=" + registeredAt +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
