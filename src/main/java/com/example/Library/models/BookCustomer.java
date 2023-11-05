package com.example.Library.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "book_customer")
public class BookCustomer {

    @EmbeddedId
    private BookCustomerId id;

    @ManyToOne
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime takenAt;

    public BookCustomerId getId() {
        return id;
    }

    public void setId(BookCustomerId id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(LocalDateTime takenAt) {
        this.takenAt = takenAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCustomer that = (BookCustomer) o;
        return Objects.equals(id, that.id) && Objects.equals(book, that.book) && Objects.equals(customer, that.customer) && Objects.equals(takenAt, that.takenAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, customer, takenAt);
    }

    @Override
    public String toString() {
        return "BookCustomer{" +
                "id=" + id +
                ", book=" + book +
                ", customer=" + customer +
                ", takenAt=" + takenAt +
                '}';
    }
}

