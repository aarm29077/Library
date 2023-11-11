package com.example.Library.repositories;

import com.example.Library.models.BookStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookStockRepository extends JpaRepository<BookStock, Long> {
        Optional<BookStock> findByBookId(Long id);
        Optional<Integer> findCurrentQuantityByBookId(Long id);
        Optional<Integer> findTotalQuantityByBookId(Long id);
}
