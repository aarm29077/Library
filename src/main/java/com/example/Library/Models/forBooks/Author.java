package com.example.Library.Models.forBooks;

import com.example.Library.Util.customAnnotations.ValidString;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "author", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "surname"}))
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long id;

    @Column(name = "name")
    @ValidString(message = "The given author's name is not valid")
    @NotBlank(message = "The name should not be empty")
    @Size(min = 2, max = 30, message = "The author's name should be between 2 and 30 characters")
    private @Getter @Setter String name;

    @Column(name = "surname")
    @NotBlank(message = "The surname should not be empty")
    @ValidString(message = "The given author's surname is not valid")
    @Size(min = 2, max = 30, message = "The author's surname should be between 2 and 30 characters")
    private @Getter @Setter String surname;

    @Column(name = "nationality")
    @NotBlank(message = "Please send nationality of this author")
    @ValidString(message = "The given author's nationality is not valid")
    @Size(min = 2, max = 30, message = "he author's nationality should be between 2 and 30 characters")
    private @Getter @Setter String nationality;

    @ManyToMany(mappedBy = "authors")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private @Getter @Setter List<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(name, author.name) && Objects.equals(surname, author.surname) && Objects.equals(nationality, author.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, nationality);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
