package com.example.Library.services;

import com.example.Library.models.Book;
import com.example.Library.models.Customer;
import com.example.Library.repositories.BookRepository;
import com.example.Library.repositories.CustomerRepository;
import com.example.Library.util.AgeOver12;
import com.example.Library.util.CheckUtils;
import com.example.Library.util.InvalidStringException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, BookRepository bookRepository) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer addCustomer(Customer customer) {
        CheckUtils.isStringValid(customer.getName());
        CheckUtils.isStringValid(customer.getSurname());
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Long id, String newName, String surname) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        CheckUtils.isStringValid(newName);
        CheckUtils.isStringValid(surname);
        customer.setName(newName);
        customer.setSurname(surname);
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Long id, Date dateOfBirth) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        customer.setDateOfBirth(dateOfBirth);
        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    @Transactional
    public void addBookToCustomer(Long customerId, Long bookId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));

        if (book.getCurrentQuantity() > 0) {
            synchronized (bookRepository) {
                if (book.getCurrentQuantity() > 0) {
                    book.setCurrentQuantity(book.getCurrentQuantity() - 1);
                    customer.getBooks().add(book);
                    book.getCustomers().add(customer);

                    customerRepository.save(customer);
                    bookRepository.save(book);
                }
            }
        } else {
            throw new IllegalStateException("The book is out of stock.");
        }
    }

    public List<Book> customerBooks(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        return new ArrayList<>(customer.getBooks());
    }

    public List<Customer> getCustomersByName(String name) {
        return customerRepository.findCustomersByName(name).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    public List<Customer> getCustomersBySurname(String surname) {
        return customerRepository.findCustomersBySurname(surname).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }
}
