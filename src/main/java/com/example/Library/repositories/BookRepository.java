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
    Boolean existsByIsbn(String isbn);

    void deleteByIsbn(String isbn);

    Optional<List<Book>> findByTitle(String title);

    Optional<List<Book>> findByIsbn(String isbn);

    Optional<List<Author>> findAuthorsById(Long bookId);

    Optional<List<Author>> findAuthorsByIsbn(String isbn);

    Optional<Date> findPublicationDateById(Long bookId);

    Optional<Date> findPublicationDateByIsbn(String isbn);

    Optional<Integer> findCurrentQuantityById(Long id);

    Optional<Integer> findCurrentQuantityByIsbn(String isbn);

    Optional<Integer> findTotalQuantityById(Long id);

    Optional<Integer> findTotalQuantityByIsbn(String isbn);
}
