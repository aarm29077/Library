package com.example.Library.services;

import com.example.Library.models.Book;
import com.example.Library.models.BookUser;
import com.example.Library.models.User;
import com.example.Library.repositories.BookUserRepository;
import com.example.Library.repositories.BookRepository;
import com.example.Library.repositories.UserRepository;
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
    private final BookRepository bookRepository;
    private final BookUserService bookUserService;
    private final BookUserRepository bookUserRepository;

    @Autowired
    public UserService(UserRepository userRepository, BookService bookService, BookRepository bookRepository, BookUserService bookCustomerService, BookUserRepository bookUserRepository) {
        this.userRepository = userRepository;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.bookUserService = bookCustomerService;
        this.bookUserRepository = bookUserRepository;
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

        for (BookUser book : user.getUserBooks()) {
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
    public boolean deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()) return false;

        userRepository.deleteById(id);
        return true;
    }

//    @Transactional
//    public User updateUser(Long id, String newName, String surname, Date dateOfBirth) {
//        User customer = findUserById(id);
//        customer.setName(newName);
//        customer.setSurname(surname);
//        customer.setDateOfBirth(dateOfBirth);
//        return userRepository.save(customer);
//    }


//    @Transactional
//    public void addBookToUser(Long userId, Long bookId) {
//        User user = findUserById(userId);
//        Book book = bookService.findBookById(bookId);
//
//        if (book.getCurrentQuantity() > 0) {
//            synchronized (bookService) {
//                if (book.getCurrentQuantity() > 0) {
//                    book.setCurrentQuantity(book.getCurrentQuantity() - 1);
//
//                    BookUser bookCustomer = new BookUser();
//                    BookUserId bookCustomerId = new BookUserId();
//                    bookCustomerId.setUser_id(userId);
//                    bookCustomerId.setBook_id(bookId);
//                    bookCustomer.setId(bookCustomerId);
//                    bookCustomer.setTakenAt(LocalDateTime.now());
//
//                    user.getUserBooks().add(bookCustomer);
//                    book.getBookCustomers().add(bookCustomer);
//
//                    // No need to manually save bookCustomer, as it should be cascaded from the associations
//                    userRepository.save(user);
//                    bookRepository.save(book);
//                }
//            }
//        } else {
//            throw new IllegalStateException("The book is out of stock.");
//        }
//    }

/*    @Transactional
    public void releaseBookFromUser(Long userId, Long bookId) {
        User user = findUserById(userId);
        Book book = bookService.findBookById(bookId);
        BookUser bookCustomer = bookUserService.findByBookIdAndCustomerId(userId, bookId);


        if (bookCustomer != null) {
            synchronized (bookService) {
                book.setCurrentQuantity(book.getCurrentQuantity() + 1);

                bookUserRepository.delete(bookCustomer);

                //             No need
//                user.getUserBooks().remove(bookUser);
//                book.getBookUsers().remove(bookUser);
                userRepository.save(user);
                bookRepository.save(book);
            }
        }
    }*/


    private void enrichUser(User user) {
        user.setRegisteredAt(LocalDateTime.now());
    }
}
