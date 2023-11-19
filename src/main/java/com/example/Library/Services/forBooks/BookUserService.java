package com.example.Library.Services.forBooks;

import com.example.Library.Models.forBooks.Book;
import com.example.Library.Models.BookUser;
import com.example.Library.Models.forUsers.User;
import com.example.Library.Repositories.BookUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookUserService {
    private final BookUserRepository bookUserRepository;

    @Autowired
    public BookUserService(BookUserRepository bookUserRepository) {
        this.bookUserRepository = bookUserRepository;
    }

    public LocalDateTime getTakenAtTime(Long bookId, Long customerId) {
        Optional<BookUser> bookUser = bookUserRepository.findByBookIdAndUserId(bookId, customerId);

        if (bookUser.isPresent()) {
            return bookUser.get().getTakenAt();
        } else {
            throw new EntityNotFoundException("Book not found for the given user.");
        }
    }

    public Optional<BookUser> findByBookIdAndUserId(Long bookId, Long userId) {
        return bookUserRepository.findByBookIdAndUserId(bookId, userId);
    }

    public BookUser create(Book book, User user) {
        BookUser bookUser = new BookUser();
        bookUser.setBook(book);
        bookUser.setUser(user);

        book.getUsers().add(bookUser);
        user.getBooks().add(bookUser);

        return bookUserRepository.save(bookUser);
    }

    @Transactional
    public boolean delete(BookUser bookUser) {
        if (findByBookIdAndUserId(bookUser.getBook().getId(), bookUser.getUser().getId()).isEmpty()) return false;

        bookUserRepository.delete(bookUser);
        return true;
    }
}
