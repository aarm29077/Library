package com.example.Library.services;

import com.example.Library.models.Author;
import com.example.Library.models.Book;
import com.example.Library.repositories.BookRepository;
import com.example.Library.util.customExceptions.BookNotFoundException;
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


    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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

    public String findPublicationDateByBookId(Long id) {
        Book resultBook = findBookById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));

        return resultBook.getPublicationDate();
    }

    public String findPublicationDateByBookIsbn(String isbn) {
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
    public void addAuthorToBook(Long bookId, Author author) {
        Book book = findBookById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));

        // Ensure that the author is not already associated with the book
        if (!book.getAuthors().contains(author)) {
            book.getAuthors().add(author);
            bookRepository.save(book);
        }
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
