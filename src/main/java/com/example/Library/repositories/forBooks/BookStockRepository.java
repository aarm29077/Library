package com.example.Library.repositories.forBooks;

import com.example.Library.models.forBooks.BookStock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookStockRepository extends JpaRepository<BookStock, Long> {
        @Lock(LockModeType.OPTIMISTIC)
        Optional<BookStock> findByBookId(Long id);
}
