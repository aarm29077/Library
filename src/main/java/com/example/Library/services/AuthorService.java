package com.example.Library.services;

import com.example.Library.models.Author;
import com.example.Library.models.Book;
import com.example.Library.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> findAuthors() {
        return authorRepository.findAll();
    }

    public List<Book> findBooksByAuthorId(Long id) {
        List<Book> books = authorRepository.findBooksById(id);
        if (books.isEmpty()) {
            throw new EntityNotFoundException("Author not found or has no books");
        }
        return books;
    }

    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public List<Author> findByAuthorNameAndAuthorSurname(String name, String surname) {
        return authorRepository.findByNameAndSurname(name, surname).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public List<Author> findByAuthorName(String name) {
        return authorRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public List<Author> findByAuthorSurname(String surname) {
        return authorRepository.findBySurname(surname).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public List<Author> findByNationality(String nationality) {
        return authorRepository.findByNationality(nationality).orElseThrow(() -> new EntityNotFoundException("No author found for this nationality"));
    }

    public String findNationalityByAuthorId(Long id) {
        return authorRepository.findNationalityById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }
}
