package com.example.Library.controller;

import com.example.Library.dto.BookDTOResponse;
import com.example.Library.dto.CustomerDTO;
import com.example.Library.models.Customer;
import com.example.Library.services.CustomerService;
import com.example.Library.services.DTOConversionService;
import com.example.Library.util.customAnnotations.ValidString;
import com.example.Library.util.customExceptions.CustomerNotCreatedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("customers")
@Validated
public class CustomerController {
    private final CustomerService customerService;
    private final DTOConversionService dtoConversionService;

    @Autowired
    public CustomerController(CustomerService customerService, DTOConversionService dtoConversionService) {
        this.customerService = customerService;
        this.dtoConversionService = dtoConversionService;
    }

    @GetMapping("/all")
    public List<CustomerDTO> getCustomers() {
        return customerService.findCustomers().stream().map(dtoConversionService::convertToCustomerDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return dtoConversionService.convertToCustomerDTO(customerService.findCustomerById(id));
    }

    @GetMapping("/{id}/books")
    public List<BookDTOResponse> getCustomerBooks(@PathVariable Long id) {
        return customerService.findCustomerBooks(id).stream().map(dtoConversionService::convertToBookDTOResponse).collect(Collectors.toList());
    }

    @GetMapping("/getByName/{name}")
    public List<CustomerDTO> getCustomersByName(@PathVariable @Validated @Size(min = 2, max = 30, message = "The customer's name should be between 2 and 30 characters") @NotBlank(message = "The customer's name should not be empty") @ValidString(message = "The given customer's name is not valid") String name) {
        return customerService.findCustomersByName(name).stream().map(dtoConversionService::convertToCustomerDTO).collect(Collectors.toList());
    }

    @GetMapping("/getBySurname/{surname}")
    public List<CustomerDTO> getCustomersBySurname(@PathVariable @Validated @Size(min = 2, max = 30, message = "The customer's surname should be between 2 and 30 characters") @NotBlank(message = "The customer's surname should not be empty") @ValidString(message = "The given customer's surname is not valid") String surname) {
        return customerService.findCustomersBySurname(surname).stream().map(dtoConversionService::convertToCustomerDTO).collect(Collectors.toList());
    }

    @GetMapping("/getByEmail/{email}")
    public CustomerDTO getCustomerByEmail(@PathVariable @Validated @Email(message = "Please provide a valid email address") @ValidString(message = "The given customer's email is not valid") String email) {
        return dtoConversionService.convertToCustomerDTO(customerService.findCustomerByEmail(email));
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerDTO customerDTO) {
        Customer customerById = customerService.findCustomerById(id);

        return dtoConversionService.convertToCustomerDTO(
                customerService.updateCustomer(
                        id, customerDTO.getName(), customerDTO.getSurname(), customerDTO.getDateOfBirth()
                )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid CustomerDTO customerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new CustomerNotCreatedException(errorMsg.toString());
        }
        boolean inserted = customerService.addCustomer(dtoConversionService.convertToCustomer(customerDTO));
        if (inserted) {
            return new ResponseEntity<>("Customer with Email " + customerDTO.getEmail() + " is inserted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Customer with Email " + customerDTO.getEmail() + " already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{customerId}/add-book/{bookId}")
    public ResponseEntity<String> addBookToCustomer(
            @PathVariable Long customerId,
            @PathVariable Long bookId
    ) {
        try {
            customerService.addBookToCustomer(customerId, bookId);
            return ResponseEntity.ok("Book added to the customer successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer or book not found");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The book is out of stock");
        }
    }

    @PostMapping("/{customerId}/release-book/{bookId}")
    public ResponseEntity<String> releaseBook(
            @PathVariable Long customerId,
            @PathVariable Long bookId
    ) {
        try {
            customerService.releaseBookFromCustomer(customerId, bookId);
            return ResponseEntity.ok("Book released successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer or book not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        boolean removed = customerService.deleteCustomer(id);

        if (removed) {
            return new ResponseEntity<>("Customer with ID " + id + " removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Customer with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

}
