package com.example.Library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @Size(min = 2, max = 30, message = "The book's title should be between 2 and 30 characters")
    @NotBlank(message = "The book's title should not be empty")
    private String title;

    @Column(name = "publication_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date publicationDate;

    @Column(name = "total_quantity")
    @Min(value = 1)
    @NotBlank(message = "Please write total quantity of this book")
    private int totalQuantity;

    @Column(name = "current_quantity")
    @Min(value = 0)
    @NotBlank(message = "Please write current quantity of this book")
    private int currentQuantity;


    @ManyToMany
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Set<Author> authors;

    @ManyToMany
    @JoinTable(
            name = "books_customers",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return totalQuantity == book.totalQuantity && currentQuantity == book.currentQuantity && Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(publicationDate, book.publicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publicationDate, totalQuantity, currentQuantity);
    }
}
