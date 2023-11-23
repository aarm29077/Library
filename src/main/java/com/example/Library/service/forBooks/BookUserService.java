package com.example.Library.service.forBooks;

import com.example.Library.models.forBooks.Book;
import com.example.Library.models.BookUser;
import com.example.Library.models.forUsers.User;
import com.example.Library.repositories.BookUserRepository;
import com.example.Library.util.customExceptions.relatedToBook.BookNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
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
            throw new BookNotFoundException("Book not found for the given user.");
        }
    }

    public Optional<BookUser> findByBookIdAndUserId(Long bookId, Long userId) {
        return bookUserRepository.findByBookIdAndUserId(bookId, userId);
    }

    @Transactional
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
