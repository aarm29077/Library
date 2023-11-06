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

    public Book findBookByBookIsbn(String isbn) {
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
        Book resultBook = findBookById(bookId);
        return resultBook.getAuthors();
    }

    public List<Author> findAuthorsByBookIsbn(String isbn) {
        Book resultBook = findBookByBookIsbn(isbn);
        return resultBook.getAuthors();
    }

    public Date findPublicationDateByBookId(Long id) {
        Book resultBook = findBookById(id);
        return resultBook.getPublicationDate();
    }

    public Date findPublicationDateByBookIsbn(String isbn) {
        Book resultBook = findBookByBookIsbn(isbn);
        return resultBook.getPublicationDate();
    }

    public Integer findCurrentQuantityByBookId(Long id) {
        Book resultBook = findBookById(id);
        return resultBook.getCurrentQuantity();
    }

    public Integer findCurrentQuantityByBookIsbn(String isbn) {
        Book resultBook = findBookByBookIsbn(isbn);
        return resultBook.getCurrentQuantity();
    }

    public Integer findTotalQuantityByBookId(Long id) {
        Book resultBook = findBookById(id);
        return resultBook.getTotalQuantity();
    }

    public Integer findTotalQuantityByBookIsbn(String isbn) {
        Book resultBook = findBookByBookIsbn(isbn);
        return resultBook.getTotalQuantity();
    }

}
