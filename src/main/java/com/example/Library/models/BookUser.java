package com.example.Library.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "book_user")
public class BookUser {

    @EmbeddedId
    private BookUserId id;

    @ManyToOne
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime takenAt;

    public BookUserId getId() {
        return id;
    }

    public void setId(BookUserId id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        BookUser that = (BookUser) o;
        return Objects.equals(id, that.id) && Objects.equals(book, that.book) && Objects.equals(user, that.user) && Objects.equals(takenAt, that.takenAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, user, takenAt);
    }

    @Override
    public String toString() {
        return "BookCustomer{" +
                "id=" + id +
                ", book=" + book +
                ", customer=" + user +
                ", takenAt=" + takenAt +
                '}';
    }
}

