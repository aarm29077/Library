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
        if (!bookRepository.existsByBookIsbn(book.getIsbn())) {
            bookRepository.save(book);
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
        return bookRepository.findByBookTitle(title).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public List<Book> findBooksByBookIsbn(String isbn) {
        return bookRepository.findByBookIsbn(isbn).orElseThrow(() -> new EntityNotFoundException("Book not found"));
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
        if (bookRepository.existsByBookIsbn(isbn)) {
            bookRepository.deleteByBookIsbn(isbn);
            return true;
        }
        return false;
    }

    public List<Author> findAuthorsByBookId(Long bookId) {
        return bookRepository.findAuthorsByBookId(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public List<Author> findAuthorsByBookIsbn(String isbn) {
        return bookRepository.findAuthorsByBookIsbn(isbn).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Date findPublicationDateByBookId(Long id) {
        return bookRepository.findPublicationDateByBookId(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Date findPublicationDateByBookIsbn(String isbn) {
        return bookRepository.findPublicationDateByBookIsbn(isbn).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Integer findCurrentQuantityByBookId(Long id) {
        return bookRepository.findCurrentQuantityByBookId(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Integer findCurrentQuantityByBookIsbn(String isbn) {
        return bookRepository.findCurrentQuantityByBookIsbn(isbn).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Integer findTotalQuantityByBookId(Long id) {
        return bookRepository.findTotalQuantityByBookId(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Integer findTotalQuantityByBookIsbn(String isbn) {
        return bookRepository.findTotalQuantityByBookIsbn(isbn).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

}
