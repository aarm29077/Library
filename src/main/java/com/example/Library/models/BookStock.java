package com.example.Library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.Objects;

@Entity
@Table(name = "book_stock", uniqueConstraints = @UniqueConstraint(columnNames = "book_id"))
public class BookStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne()
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @Column(name = "current_quantity")
    @Min(value = 0, message = "Minimum current quantity should be 0")
    private int currentQuantity;

    @Column(name = "total_quantity")
    @Min(value = 1, message = "Minimum total quantity should be 1")
    private int totalQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookStock bookStock = (BookStock) o;
        return Objects.equals(id, bookStock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, currentQuantity, totalQuantity);
    }

    @Override
    public String toString() {
        return "BookStock{" +
                "id=" + id +
                ", currentQuantity=" + currentQuantity +
                ", totalQuantity=" + totalQuantity +
                '}';
    }
}
