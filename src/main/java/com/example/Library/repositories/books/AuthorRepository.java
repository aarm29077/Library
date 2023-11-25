package com.example.Library.repositories.books;

import com.example.Library.models.books.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByNameAndSurname(String name, String surname);

    Optional<List<Author>> findByName(String name);

    Optional<List<Author>> findBySurname(String surname);

    Optional<List<Author>> findByNationality(String nationality);

}
