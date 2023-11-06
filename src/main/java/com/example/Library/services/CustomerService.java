package com.example.Library.services;

import com.example.Library.models.Book;
import com.example.Library.models.BookCustomer;
import com.example.Library.models.BookCustomerId;
import com.example.Library.models.Customer;
import com.example.Library.repositories.BookCustomerRepository;
import com.example.Library.repositories.BookRepository;
import com.example.Library.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final BookCustomerService bookCustomerService;
    private final BookCustomerRepository bookCustomerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, BookService bookService, BookRepository bookRepository, BookCustomerService bookCustomerService, BookCustomerRepository bookCustomerRepository) {
        this.customerRepository = customerRepository;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.bookCustomerService = bookCustomerService;
        this.bookCustomerRepository = bookCustomerRepository;
    }

    public List<Customer> findCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    public List<Customer> findCustomersByName(String name) {
        return customerRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    public List<Customer> findCustomersBySurname(String surname) {
        return customerRepository.findBySurname(surname).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    @Transactional
    public boolean addCustomer(Customer customer) {
        if (!customerRepository.existsByEmail(customer.getEmail())) {
            enrichCustomer(customer);
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public Customer updateCustomer(Long id, String newName, String surname, Date dateOfBirth) {
        Customer customer = findCustomerById(id);
        customer.setName(newName);
        customer.setSurname(surname);
        customer.setDateOfBirth(dateOfBirth);
        return customerRepository.save(customer);
    }

    @Transactional
    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public void addBookToCustomer(Long customerId, Long bookId) {
        Customer customer = findCustomerById(customerId);
        Book book = bookService.findBookById(bookId);

        if (book.getCurrentQuantity() > 0) {
            synchronized (bookService) {
                if (book.getCurrentQuantity() > 0) {
                    book.setCurrentQuantity(book.getCurrentQuantity() - 1);

                    BookCustomer bookCustomer = new BookCustomer();
                    BookCustomerId bookCustomerId = new BookCustomerId();
                    bookCustomerId.setCustomer_id(customerId);
                    bookCustomerId.setBook_id(bookId);
                    bookCustomer.setId(bookCustomerId);
                    bookCustomer.setTakenAt(LocalDateTime.now());

                    customer.getCustomerBooks().add(bookCustomer);
                    book.getBookCustomers().add(bookCustomer);

                    // No need to manually save bookCustomer, as it should be cascaded from the associations
                    customerRepository.save(customer);
                    bookRepository.save(book);
                }
            }
        } else {
            throw new IllegalStateException("The book is out of stock.");
        }
    }

    @Transactional
    public void releaseBookFromCustomer(Long customerId, Long bookId) {
        Customer customer = findCustomerById(customerId);
        Book book = bookService.findBookById(bookId);
        BookCustomer bookCustomer = bookCustomerService.findByBookIdAndCustomerId(customerId, bookId);


        if (bookCustomer != null) {
            synchronized (bookService) {
                book.setCurrentQuantity(book.getCurrentQuantity() + 1);

                bookCustomerRepository.delete(bookCustomer);

                //             No need
//                customer.getCustomerBooks().remove(bookCustomer);
//                book.getBookCustomers().remove(bookCustomer);
                customerRepository.save(customer);
                bookRepository.save(book);
            }
        }
    }

    public List<Book> findCustomerBooks(Long id) {
        Customer customer = findCustomerById(id);

        List<Book> customerBooks = new ArrayList<>();

        for (BookCustomer bookCustomer : customer.getCustomerBooks()) {
            customerBooks.add(bookCustomer.getBook());
        }

        return customerBooks;
    }


    private void enrichCustomer(Customer customer) {
        customer.setRegisteredAt(LocalDateTime.now());
    }
}
