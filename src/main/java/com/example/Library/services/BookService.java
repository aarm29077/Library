package com.example.Library.services;

import com.example.Library.models.Author;
import com.example.Library.models.Book;
import com.example.Library.repositories.BookRepository;
import com.example.Library.util.CheckUtils;
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
    public Book addBook(Book book) {
        CheckUtils.isStringValid(book.getTitle());
        return bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    @Transactional
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    public List<Author> getAuthorsByBookId(Long bookId) {
        return bookRepository.findAuthorsByBookId(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public List<Author> getAuthorsByBookTitle(String title) {
        return bookRepository.findAuthorsByBookTitle(title).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Date getPublicationDateByBookTitle(String title) {
        return bookRepository.findPublicationDateByBookTitle(title).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Date getPublicationDateByBookId(Long id) {
        return bookRepository.findPublicationDateByBookId(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Integer getTotalQuantityByBookTitle(String title) {
        return bookRepository.findTotalQuantityByBookTitle(title).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Integer getTotalQuantityByBookId(Long id) {
        return bookRepository.findTotalQuantityByBookId(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Integer getCurrentQuantityByBookTitle(String title) {
        return bookRepository.findCurrentQuantityByBookTitle(title).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Integer getCurrentQuantityByBookId(Long id) {
        return bookRepository.findCurrentQuantityByBookId(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }
}
