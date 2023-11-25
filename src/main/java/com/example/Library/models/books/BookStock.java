package com.example.Library.models.books;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "book_stock", uniqueConstraints = @UniqueConstraint(columnNames = "book_id"))
public class BookStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @Getter @Setter Long id;

    @OneToOne()
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private @Getter @Setter Book book;

    @Column(name = "current_quantity")
    @Min(value = 0, message = "Minimum current quantity should be 0")
    private @Getter @Setter int currentQuantity;

    @Column(name = "total_quantity")
    @Min(value = 1, message = "Minimum total quantity should be 1")
    private @Getter @Setter int totalQuantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookStock bookStock = (BookStock) o;
        return currentQuantity == bookStock.currentQuantity && totalQuantity == bookStock.totalQuantity && Objects.equals(id, bookStock.id) && Objects.equals(book, bookStock.book);
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
