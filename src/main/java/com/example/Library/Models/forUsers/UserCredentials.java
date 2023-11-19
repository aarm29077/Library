package com.example.Library.Models.forUsers;

import com.example.Library.Util.customAnnotations.ValidString;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "user_credentials")
public class UserCredentials {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long id;

    @Column(name = "username", unique = true)
    @Size(min = 2, max = 30, message = "The username should be between 2 and 30 characters")
    @NotBlank(message = "The username should not be empty")
    @ValidString(message = "The given string is not valid")
    private @Getter @Setter String username;

    @Column(name = "password", unique = true)
    @NotBlank(message = "The password should not be empty")
    @ValidString(message = "The given string is not valid")
    private @Getter @Setter String password;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private @Getter @Setter User user;

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
