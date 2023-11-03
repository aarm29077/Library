package com.example.Library.repositories;

import com.example.Library.models.Author;
import com.example.Library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<List<Author>> findBooksByAuthorNameAndSurname(String name, String surname);

    Optional<List<Book>> findBooksByAuthorId(Long id);

    Optional<List<Author>> findAuthorsByAuthorName(String name);

    Optional<List<Author>> findAuthorsByAuthorSurname(String surname);

    Optional<List<Author>> findAuthorsByNationality(String nationality);

    Optional<String> findNationalityByAuthorId(Long id);
}
