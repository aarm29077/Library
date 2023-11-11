package com.example.Library.services;

import com.example.Library.models.Book;
import com.example.Library.models.BookStock;
import com.example.Library.repositories.BookStockRepository;
import com.example.Library.util.customExceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookStockService {
    private final BookStockRepository bookStockRepository;

    @Autowired
    public BookStockService(BookStockRepository bookStockRepository) {
        this.bookStockRepository = bookStockRepository;
    }

    @Transactional
    public boolean add(Long id, int quantity) {
        Optional<BookStock> byBookId = bookStockRepository.findByBookId(id);
        if (byBookId.isEmpty()) {
            return false;
        }
        BookStock bookStock = byBookId.get();
        bookStock.setTotalQuantity(bookStock.getTotalQuantity() + quantity);
        bookStockRepository.save(bookStock);
        return true;
    }
    //    public BookStock create(Book book){
//        Optional<BookStock> bookStock = bookStockRepository.findByBookId(book.getId());
//        if (bookStock.isPresent()){
//            add(book.getId(),book.get)
//        }
//    }
    public Optional<Integer> findCurrentQuantityByBookId(Long id) {
        return bookStockRepository.findCurrentQuantityByBookId(id);
    }

    public Optional<Integer> findTotalQuantityByBookId(Long id) {
        return bookStockRepository.findTotalQuantityByBookId(id);
    }
}
