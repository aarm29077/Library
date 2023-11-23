package com.example.Library.repositories.forBooks;

import com.example.Library.models.forBooks.Book;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    void deleteByIsbn(String isbn);

    Optional<List<Book>> findByTitle(String title);

    Optional<Book> findByIsbn(String isbn);

}
