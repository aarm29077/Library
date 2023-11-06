package com.example.Library.repositories;

import com.example.Library.models.Author;
import com.example.Library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Boolean existsByNameAndSurname(String name, String surname);

    Optional<Author> findByNameAndSurname(String name, String surname);

//    List<Book> findBooksById(Long id);

    Optional<List<Author>> findByName(String name);

    Optional<List<Author>> findBySurname(String surname);

    Optional<List<Author>> findByNationality(String nationality);

}
