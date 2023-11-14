package com.example.Library.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "book_user")
public class BookUser {

    @EmbeddedId
    private @Getter @Setter BookUserId id;

    @ManyToOne
    @MapsId("book_id")
    private @Getter @Setter Book book;

    @ManyToOne
    @MapsId("user_id")
    private @Getter @Setter User user;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private @Getter @Setter LocalDateTime takenAt;

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

