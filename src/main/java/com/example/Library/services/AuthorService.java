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
        return authorRepository.findBooksByAuthorId(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public List<Author> findBooksByAuthorNameAndSurname(String name, String surname) {
        return authorRepository.findBooksByAuthorNameAndSurname(name, surname).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public List<Author> findAuthorsByAuthorName(String name) {
        return authorRepository.findAuthorsByAuthorName(name).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public List<Author> findAuthorsByAuthorSurname(String surname) {
        return authorRepository.findAuthorsByAuthorSurname(surname).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public List<Author> findAuthorsByNationality(String nationality) {
        return authorRepository.findAuthorsByNationality(nationality).orElseThrow(() -> new EntityNotFoundException("No author found for this nationality"));
    }

    public String findNationalityByAuthorId(Long id) {
        return authorRepository.findNationalityByAuthorId(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }
}
