package com.example.Library.services;

import com.example.Library.models.Author;
import com.example.Library.models.Book;
import com.example.Library.repositories.AuthorRepository;
import com.example.Library.util.customExceptions.AuthorExistsException;
import com.example.Library.util.customExceptions.AuthorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public Author createAuthor(Author author) {
        Optional<Author> byNameAndSurname = authorRepository.findByNameAndSurname(author.getName(), author.getSurname());
        if (byNameAndSurname.isPresent()) throw new AuthorExistsException("Author already exist");

        return authorRepository.save(author);

    }

    public Optional<List<Author>> findAuthors() {
        return Optional.of(authorRepository.findAll());
    }


    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    public Optional<Author> findByAuthorNameAndAuthorSurname(String name, String surname) {
        return authorRepository.findByNameAndSurname(name, surname);
    }

    public Optional<List<Author>> findByAuthorName(String name) {
        return authorRepository.findByName(name);
    }

    public Optional<List<Author>> findByAuthorSurname(String surname) {
        return authorRepository.findBySurname(surname);
    }

    public Optional<List<Author>> findByNationality(String nationality) {
        return authorRepository.findByNationality(nationality);
    }

    public String findNationalityByAuthorId(Long id) {
        Author byId = findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found"));
        return byId.getNationality();
    }

    public Optional<List<Book>> findBooksByAuthorId(Long id) {
        Author resultAuthor = findById(id).orElseThrow(() -> new AuthorNotFoundException("Author not found"));
        return Optional.of(resultAuthor.getBooks());
    }

    public List<Author> findAuthorsByIds(List<Long> ids) {
        List<Author> authors = new ArrayList<>();
        for (Long id : ids) {
            Optional<Author> byId = findById(id);
            if (byId.isEmpty()) {
                throw new AuthorNotFoundException("Author Not Found");
            }
            authors.add(byId.get());
        }
        return authors;
    }
}
