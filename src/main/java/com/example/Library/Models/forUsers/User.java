package com.example.Library.Models.forUsers;

import com.example.Library.Models.BookUser;
import com.example.Library.Models.forRoles.Role;
import com.example.Library.Util.customAnnotations.AgeOver12;
import com.example.Library.Util.customAnnotations.ValidString;
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

    @Column(name = "registered_at")
    private @Getter @Setter LocalDateTime registeredAt;

    @Email(message = "Please provide a valid email address")
    @ValidString(message = "The given user's email is not valid")
    private @Getter @Setter String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private @Getter @Setter List<BookUser> books;

    @OneToOne(mappedBy = "user",orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private @Getter @Setter UserCredentials credentials;

    @ManyToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private @Getter @Setter Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User customer = (User) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, dateOfBirth, registeredAt, email, role);
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
                ", books=" + books +
                ", role=" + role +
                '}';
    }
}
