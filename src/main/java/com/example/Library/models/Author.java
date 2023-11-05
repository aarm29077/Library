package com.example.Library.models;

import com.example.Library.util.customAnnotations.ValidString;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "author", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "surname"}))
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @ValidString(message = "The given author's name is not valid")
    @NotBlank(message = "The name should not be empty")
    @Size(min = 2, max = 30, message = "The author's name should be between 2 and 30 characters")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "The surname should not be empty")
    @ValidString(message = "The given author's surname is not valid")
    @Size(min = 2, max = 30, message = "The author's surname should be between 2 and 30 characters")
    private String surname;

    @Column(name = "nationality")
    @NotBlank(message = "Please send nationality of this author")
    @ValidString(message = "The given author's nationality is not valid")
    @Size(min = 2, max = 30, message = "he author's nationality should be between 2 and 30 characters")
    private String nationality;

    @ManyToMany(mappedBy = "authors")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private List<Book> books;

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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

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
