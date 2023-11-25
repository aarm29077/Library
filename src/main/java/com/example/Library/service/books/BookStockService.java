package com.example.Library.service.books;

import com.example.Library.models.books.Book;
import com.example.Library.models.books.BookStock;
import com.example.Library.repositories.books.BookStockRepository;
import com.example.Library.util.customExceptions.book.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookStockService {
    private final BookStockRepository bookStockRepository;
    private final BookService bookService;

    @Autowired
    public BookStockService(BookStockRepository bookStockRepository, BookService bookService) {
        this.bookStockRepository = bookStockRepository;
        this.bookService = bookService;
    }

    @Transactional
    public BookStock quantityManagement(Long bookId, int quantity) {
        Optional<Book> bookById = bookService.findBookById(bookId);
        if (bookById.isEmpty()) throw new BookNotFoundException("Book not found");

        Optional<BookStock> bookStockByBookId = findStockInformationByBookId(bookId);
        if (bookStockByBookId.isEmpty()) {
            return createStockInformation(bookById.get(), quantity);
        }
        return updateStockInformation(bookStockByBookId.get(), quantity);
    }

    @Transactional
    public BookStock createStockInformation(Book book, int quantity) {
        BookStock bookStock = new BookStock();
        bookStock.setBook(book);
        bookStock.setCurrentQuantity(quantity);
        bookStock.setTotalQuantity(quantity);
        return bookStockRepository.save(bookStock);
    }

    @Transactional
    public BookStock updateStockInformation(BookStock bookStock, int quantity) {
        bookStock.setCurrentQuantity(bookStock.getCurrentQuantity() + quantity);
        bookStock.setTotalQuantity(bookStock.getTotalQuantity() + quantity);
        return bookStockRepository.save(bookStock);
    }


    public Optional<BookStock> findStockInformationByBookId(Long bookId) {
        return bookStockRepository.findByBookId(bookId);
    }

    public Optional<Integer> findCurrentQuantityByBookId(Long bookId) {
        BookStock stockInformationByBookId = findStockInformationByBookId(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));
        return Optional.of(stockInformationByBookId.getCurrentQuantity());

    }

    public Optional<Integer> findTotalQuantityByBookId(Long bookId) {
        BookStock stockInformationByBookId = findStockInformationByBookId(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));
        return Optional.of(stockInformationByBookId.getTotalQuantity());
    }
}
