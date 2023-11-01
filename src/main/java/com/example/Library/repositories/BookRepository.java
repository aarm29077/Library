package com.example.Library.repositories;

import com.example.Library.models.Author;
import com.example.Library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<List<Author>> findAuthorsByBookId(Long bookId);

    Optional<List<Author>> findAuthorsByBookTitle(String bookTitle);

    Optional<Date> findPublicationDateByBookTitle(String title);

    Optional<Date> findPublicationDateByBookId(Long bookId);

    Optional<Integer> findTotalQuantityByBookTitle(String title);

    Optional<Integer> findTotalQuantityByBookId(Long id);

    Optional<Integer> findCurrentQuantityByBookTitle(String title);

    Optional<Integer> findCurrentQuantityByBookId(Long id);
}
