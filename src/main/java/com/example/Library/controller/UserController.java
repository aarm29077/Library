package com.example.Library.controller;

import com.example.Library.dto.UserDTORequest;
import com.example.Library.models.Book;
import com.example.Library.models.User;
import com.example.Library.services.UserService;
import com.example.Library.services.DTOConversionService;
import com.example.Library.util.customAnnotations.ValidString;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
@Validated
public class UserController {
    private final UserService userService;
    private final DTOConversionService dtoConversionService;

    @Autowired
    public UserController(UserService userService, DTOConversionService dtoConversionService) {
        this.userService = userService;
        this.dtoConversionService = dtoConversionService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getUsers() {
        Optional<List<User>> users = userService.findUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users.get().stream().map(dtoConversionService::convertToUserDTOResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> userById = userService.findUserById(id);
        if (userById.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dtoConversionService.convertToUserDTOResponse(userById.get()), HttpStatus.OK);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<?> getUserBooks(@PathVariable Long id) {
        Optional<List<Book>> userBooks = userService.findUserBooks(id);
        if (userBooks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userBooks.get().stream().map(dtoConversionService::convertToBookDTOResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<?> getUsersByName(@PathVariable @Validated @Size(min = 2, max = 30, message = "The customer's name should be between 2 and 30 characters") @NotBlank(message = "The customer's name should not be empty") @ValidString(message = "The given customer's name is not valid") String name) {
        Optional<List<User>> usersByName = userService.findUsersByName(name);
        if (usersByName.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usersByName.get().stream().map(dtoConversionService::convertToUserDTOResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/getBySurname/{surname}")
    public ResponseEntity<?> getUsersBySurname(@PathVariable @Validated @Size(min = 2, max = 30, message = "The customer's surname should be between 2 and 30 characters") @NotBlank(message = "The customer's surname should not be empty") @ValidString(message = "The given customer's surname is not valid") String surname) {
        Optional<List<User>> usersBySurname = userService.findUsersBySurname(surname);
        if (usersBySurname.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usersBySurname.get().stream().map(dtoConversionService::convertToUserDTOResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable @Validated @Email(message = "Please provide a valid email address") @ValidString(message = "The given customer's email is not valid") String email) {
        Optional<User> userByEmail = userService.findUserByEmail(email);
        if (userByEmail.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dtoConversionService.convertToUserDTOResponse(userByEmail.get()), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid UserDTORequest userDTORequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        boolean inserted = userService.createUser(dtoConversionService.convertToUser(userDTORequest));
        if (inserted) {
            return new ResponseEntity<>("Customer with Email " + userDTORequest.getEmail() + " is inserted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Customer with Email " + userDTORequest.getEmail() + " already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean removed = userService.deleteUser(id);

        if (removed) {
            return new ResponseEntity<>("User with ID " + id + " removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/{id}")
//    public UserDTO updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
//        User customerById = userService.findUserById(id);
//
//        return dtoConversionService.convertToUserDTO(
//                userService.updateUser(
//                        id, userDTO.getName(), userDTO.getSurname(), userDTO.getDateOfBirth()
//                )
//        );
//    }
//
//
//    @PostMapping("/{userId}/add-book/{bookId}")
//    public ResponseEntity<String> addBookToUser(
//            @PathVariable Long userId,
//            @PathVariable Long bookId
//    ) {
//        try {
//            userService.addBookToUser(userId, bookId);
//            return ResponseEntity.ok("Book added to the user successfully");
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or book not found");
//        } catch (IllegalStateException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The book is out of stock");
//        }
//    }
//
//    @PostMapping("/{userId}/release-book/{bookId}")
//    public ResponseEntity<String> releaseBook(
//            @PathVariable Long userId,
//            @PathVariable Long bookId
//    ) {
//        try {
//            userService.releaseBookFromUser(userId, bookId);
//            return ResponseEntity.ok("Book released successfully");
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or book not found");
//        }
//    }


}
