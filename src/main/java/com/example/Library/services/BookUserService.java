package com.example.Library.services;

import com.example.Library.models.Book;
import com.example.Library.models.BookUser;
import com.example.Library.models.User;
import com.example.Library.repositories.BookUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookUserService {
    private final BookUserRepository bookUserRepository;

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
