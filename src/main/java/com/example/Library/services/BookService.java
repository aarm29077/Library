package com.example.Library.services;

import com.example.Library.models.Author;
import com.example.Library.models.Book;
import com.example.Library.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public boolean addBook(Book book) {
        if (!bookRepository.existsByIsbn(book.getIsbn())) {
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public List<Book> findBooksByBookTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public List<Book> findBooksByBookIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    @Transactional
    public boolean deleteBookByBookId(Long bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteBookByBookIsbn(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            bookRepository.deleteByIsbn(isbn);
            return true;
        }
        return false;
    }

    public List<Author> findAuthorsByBookId(Long bookId) {
        return bookRepository.findAuthorsById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public List<Author> findAuthorsByBookIsbn(String isbn) {
        return bookRepository.findAuthorsByIsbn(isbn).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Date findPublicationDateByBookId(Long id) {
        return bookRepository.findPublicationDateById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Date findPublicationDateByBookIsbn(String isbn) {
        return bookRepository.findPublicationDateByIsbn(isbn).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Integer findCurrentQuantityByBookId(Long id) {
        return bookRepository.findCurrentQuantityById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Integer findCurrentQuantityByBookIsbn(String isbn) {
        return bookRepository.findCurrentQuantityByIsbn(isbn).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Integer findTotalQuantityByBookId(Long id) {
        return bookRepository.findTotalQuantityById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Integer findTotalQuantityByBookIsbn(String isbn) {
        return bookRepository.findTotalQuantityByIsbn(isbn).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

}
