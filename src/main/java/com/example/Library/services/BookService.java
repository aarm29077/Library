package com.example.Library.services;

import com.example.Library.models.Author;
import com.example.Library.models.Book;
import com.example.Library.repositories.AuthorRepository;
import com.example.Library.repositories.BookUserRepository;
import com.example.Library.repositories.BookRepository;
import com.example.Library.util.customExceptions.AuthorNotFoundException;
import com.example.Library.util.customExceptions.BookExistsException;
import com.example.Library.util.customExceptions.BookNotFoundException;
import com.example.Library.util.customExceptions.InvalidAuthorException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final BookUserRepository bookCustomerRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, BookUserRepository bookCustomerRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.bookCustomerRepository = bookCustomerRepository;
        this.authorRepository = authorRepository;
    }

    public Optional<List<Book>> findAllBook() {
        return Optional.of(bookRepository.findAll());
    }

    public Optional<Book> findBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    public Optional<List<Book>> findBooksByBookTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Optional<Book> findBookByBookIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }


    public Optional<List<Author>> findAuthorsByBookId(Long bookId) {
        Book resultBook = findBookById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));
        return Optional.of(resultBook.getAuthors());
    }

    public Optional<List<Author>> findAuthorsByBookIsbn(String isbn) {
        Book resultBook = findBookByBookIsbn(isbn).orElseThrow(() -> new BookNotFoundException("Book not found"));
        return Optional.of(resultBook.getAuthors());
    }

    public Date findPublicationDateByBookId(Long id) {
        Book resultBook = findBookById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));

        return resultBook.getPublicationDate();
    }

    public Date findPublicationDateByBookIsbn(String isbn) {
        Book resultBook = findBookByBookIsbn(isbn).orElseThrow(() -> new BookNotFoundException("Book not found"));

        return resultBook.getPublicationDate();
    }

    @Transactional
    public boolean createBook(Book book) {
        Optional<Book> byIsbn = bookRepository.findByIsbn(book.getIsbn());
        if (byIsbn.isPresent()) return false;

        bookRepository.save(book);
        return true;
    }

    @Transactional
    public boolean deleteBookByBookId(Long bookId) {
        if (findBookById(bookId).isEmpty()) return false;

        bookRepository.deleteById(bookId);
        return true;

    }

    @Transactional
    public boolean deleteBookByBookIsbn(String isbn) {
        if (bookRepository.findByIsbn(isbn).isEmpty()) return false;

        bookRepository.deleteByIsbn(isbn);
        return true;
    }

}
