package com.example.Library.services;

import com.example.Library.models.Author;
import com.example.Library.models.Book;
import com.example.Library.repositories.AuthorRepository;
import com.example.Library.util.CheckUtils;
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
        CheckUtils.isStringValid(author.getName());
        CheckUtils.isStringValid(author.getSurname());
        CheckUtils.isStringValid(author.getNationality());
        return authorRepository.save(author);
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public List<Book> getBooksByAuthorId(Long id) {
        return authorRepository.findBooksByAuthorId(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public List<Book> getBooksByAuthorNameAndSurname(String name, String surname) {
        return authorRepository.findBooksByAuthorNameAndSurname(name, surname).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public List<Author> findAuthorsByAuthorName(String name) {
        return authorRepository.findAuthorsByAuthorName(name).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public List<Author> getAuthorsByAuthorSurname(String surname) {
        return authorRepository.findAuthorsByAuthorSurname(surname).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    public List<Author> getAuthorsByNationality(String nationality) {
        return authorRepository.findAuthorsByNationality(nationality).orElseThrow(() -> new EntityNotFoundException("No author found for this nationality"));
    }

    public String getNationalityByAuthorId(Long id) {
        return authorRepository.findNationalityByAuthorId(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }
}
