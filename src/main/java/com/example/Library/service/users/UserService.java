package com.example.Library.service.users;

import com.example.Library.models.*;
import com.example.Library.models.books.Book;
import com.example.Library.models.BookUserId;
import com.example.Library.models.roles.Role;
import com.example.Library.models.users.User;
import com.example.Library.repositories.users.UserRepository;
import com.example.Library.service.books.BookService;
import com.example.Library.service.books.BookUserService;
import com.example.Library.service.roles.RoleService;
import com.example.Library.util.customExceptions.book.BookNotAvailableException;
import com.example.Library.util.customExceptions.book.BookNotFoundException;
import com.example.Library.util.customExceptions.mainAdmin.MainAdminDeletionException;
import com.example.Library.util.customExceptions.user.UserExistsException;
import com.example.Library.util.customExceptions.user.UserNotFoundException;
import com.example.Library.util.roles.ROLE;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BookService bookService;
    private final BookUserService bookUserService;
    private final RoleService roleService;

    @Value("${main_admin_mail}")
    private String adminMail;

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

    public Optional<String> findUserRoleByUserId(Long id) {
        User user = findUserById(id).orElseThrow(() -> new UserNotFoundException("User not found  with given id"));

        return Optional.of(user.getRole().getRoleName().toString());
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
/*

    @Transactional
    public Optional<User> setRoleToUser(Long id, ROLE role) {
        Optional<User> userById = findUserById(id);
        if (userById.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        Role roleByRoleName = roleService.findRoleByRoleName(role);

        if (userById.get().getRole().equals(roleByRoleName)) {
            return Optional.empty();
        }

        userById.get().setRole(roleByRoleName);

        return Optional.of(updateUser(userById.get()));
    }
*/

    @Transactional
    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new UserExistsException("User with email " + user.getEmail() + " already exist");

        if (user.getCredentials() != null) {
            user.getCredentials().setUser(user);
        }

        user.setRegisteredAt(LocalDateTime.now());
        return userRepository.save(user);

    }

    @Transactional
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public boolean deleteUser(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) return false;

        if (byId.get().getEmail().equals(adminMail)) throw new MainAdminDeletionException("Cannot delete main admin");

        userRepository.deleteById(id);
        return true;
    }

    @Transactional
    public boolean updateUserRole(Long requesterId, Long userToBeUpdatedId, ROLE newRole) throws AccessDeniedException {

        User requesterUser = findUserById(requesterId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userToBeUpdatedId));

        if (!requesterUser.getEmail().equals(adminMail)) {
            throw new AccessDeniedException("The admin with id " + requesterId + " can not change role");
        }

        User userToBeUpdated = findUserById(userToBeUpdatedId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userToBeUpdatedId));

        if (userToBeUpdated.getRole().getRoleName() == newRole) return false;

        Role savedRole = roleService.GETorSAVE(newRole);

        userToBeUpdated.setRole(savedRole);

        updateUser(userToBeUpdated);
        return true;
    }

    private final static Map<Long, Object> lockObjects = new ConcurrentHashMap<>();

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean takeBook(Long userId, Long bookId) {
        synchronized (lockObjects.computeIfAbsent(bookId, any -> new Object())) {
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

//                Optional.ofNullable(book.getUsers()).orElseGet(ArrayList::new).add(bookUser);

                // No need to manually save bookCustomer, as it should be cascaded from the associations
                bookService.updateBook(book);
                updateUser(user);
                return true;
            }
        else {
                throw new BookNotAvailableException("The book is out of stock.");
            }
        }
    }

    @Transactional
    public boolean releaseBook(Long userId, Long bookId) {
        synchronized (lockObjects.computeIfAbsent(bookId, any -> new Object())) {

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
    }
}
