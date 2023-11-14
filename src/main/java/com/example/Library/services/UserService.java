package com.example.Library.services;

import com.example.Library.models.Book;
import com.example.Library.models.BookUser;
import com.example.Library.models.BookUserId;
import com.example.Library.models.User;
import com.example.Library.repositories.UserRepository;
import com.example.Library.util.customExceptions.BookNotAvailableException;
import com.example.Library.util.customExceptions.BookNotFoundException;
import com.example.Library.util.customExceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BookService bookService;
    private final BookUserService bookUserService;

    @Autowired
    public UserService(UserRepository userRepository, BookService bookService, BookUserService bookCustomerService) {
        this.userRepository = userRepository;
        this.bookService = bookService;
        this.bookUserService = bookCustomerService;
    }

    public Optional<List<User>> findUsers() {
        return Optional.of(userRepository.findAll());
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<List<User>> findUsersByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<List<User>> findUsersBySurname(String surname) {
        return userRepository.findBySurname(surname);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<List<Book>> findUserBooks(Long id) {
        Optional<User> userById = findUserById(id);
        if (userById.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        User user = userById.get();

        List<Book> userBooks = new ArrayList<>();

        for (BookUser book : user.getBooks()) {
            userBooks.add(book.getBook());
        }

        return Optional.of(userBooks);
    }

    @Transactional
    public boolean createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) return false;

        enrichUser(user);
        userRepository.save(user);
        return true;
    }

    @Transactional
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public boolean deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()) return false;

        userRepository.deleteById(id);
        return true;
    }

    @Transactional
    public boolean takeBook(Long userId, Long bookId) {
        User user = findUserById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Book book = bookService.findBookById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));

        Optional<BookUser> byBookIdAndCustomerId = bookUserService.findByBookIdAndUserId(bookId, userId);
        if (byBookIdAndCustomerId.isPresent()) {
            return false;
        }

        if (book.getBookStock().getCurrentQuantity() > 0) {
            book.getBookStock().setCurrentQuantity(book.getBookStock().getCurrentQuantity() - 1);

            BookUserId bookUserId = new BookUserId();
            bookUserId.setUser_id(userId);
            bookUserId.setBook_id(bookId);

            BookUser bookUser = new BookUser();
            bookUser.setId(bookUserId);
            bookUser.setTakenAt(LocalDateTime.now());
            bookUser.setBook(book);
            bookUser.setUser(user);

            user.getBooks().add(bookUser);
            book.getUsers().add(bookUser);


            // No need to manually save bookCustomer, as it should be cascaded from the associations
            bookService.updateBook(book);
            updateUser(user);
            return true;
        } else {
            throw new BookNotAvailableException("The book is out of stock.");
        }
    }


    @Transactional
    public boolean releaseBook(Long userId, Long bookId) {
        User user = findUserById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Book book = bookService.findBookById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));

        Optional<BookUser> byBookIdAndCustomerId = bookUserService.findByBookIdAndUserId(bookId, userId);
        if (byBookIdAndCustomerId.isEmpty()) {
            return false;
        }

        book.getBookStock().setCurrentQuantity(book.getBookStock().getCurrentQuantity() + 1);

        bookUserService.delete(byBookIdAndCustomerId.get());


        //             No need
//                user.getUserBooks().remove(bookUser);
//                book.getBookUsers().remove(bookUser);
        bookService.updateBook(book);
        updateUser(user);

        return true;
    }


    private void enrichUser(User user) {
        user.setRegisteredAt(LocalDateTime.now());
    }
}
