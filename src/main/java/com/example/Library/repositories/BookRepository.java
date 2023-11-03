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
    Boolean existsByBookIsbn(String isbn);

    void deleteByBookIsbn(String isbn);

    Optional<List<Book>> findByBookTitle(String title);

    Optional<List<Book>> findByBookIsbn(String isbn);

    Optional<List<Author>> findAuthorsByBookId(Long bookId);

    Optional<List<Author>> findAuthorsByBookIsbn(String isbn);

    Optional<Date> findPublicationDateByBookId(Long bookId);

    Optional<Date> findPublicationDateByBookIsbn(String isbn);

    Optional<Integer> findTotalQuantityByBookId(Long id);

    Optional<Integer> findTotalQuantityByBookIsbn(String isbn);

    Optional<Integer> findCurrentQuantityByBookId(Long id);

    Optional<Integer> findCurrentQuantityByBookIsbn(String isbn);


}
