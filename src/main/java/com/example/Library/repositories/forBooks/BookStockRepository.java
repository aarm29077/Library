package com.example.Library.repositories.forBooks;

import com.example.Library.models.forBooks.BookStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookStockRepository extends JpaRepository<BookStock, Long> {
        Optional<BookStock> findByBookId(Long id);

}
