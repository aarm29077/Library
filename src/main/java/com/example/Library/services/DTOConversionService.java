package com.example.Library.services;

import com.example.Library.dto.*;
import com.example.Library.models.Author;
import com.example.Library.models.Book;
import com.example.Library.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DTOConversionService {

    private final ModelMapper modelMapper;
    private final AuthorService authorService;

    @Autowired
    public DTOConversionService(ModelMapper modelMapper, AuthorService authorService) {
        this.modelMapper = modelMapper;
        this.authorService = authorService;
    }


    public BookDTOResponse convertToBookDTOResponse(Book book) {
        BookDTOResponse bookDTOResponse = new BookDTOResponse();

        List<AuthorDTOResponse> authorDTOResponse = book.getAuthors().stream().map(this::convertToAuthorDTOResponse).collect(Collectors.toList());

        bookDTOResponse.setTitle(book.getTitle());
        bookDTOResponse.setPublicationDate(book.getPublicationDate());
        bookDTOResponse.setIsbn(book.getIsbn());
        bookDTOResponse.setCurrentQuantity(book.getBookStock().getCurrentQuantity());
        bookDTOResponse.setTotalQuantity(book.getBookStock().getTotalQuantity());

        bookDTOResponse.setAuthors(authorDTOResponse);

        return bookDTOResponse;
    }

    public AuthorDTOResponse convertToAuthorDTOResponse(Author author) {
        return modelMapper.map(author, AuthorDTOResponse.class);
    }

    public Book convertToBook(BookDTORequest book1) {
        Book book = new Book();

        book.setTitle(book1.getTitle());
        book.setPublicationDate(book1.getPublicationDate());
        book.setIsbn(book1.getIsbn());
        book.getBookStock().setCurrentQuantity(book1.getQuantity());
        book.getBookStock().setTotalQuantity(book1.getQuantity());

        return book;
    }

    public Author convertToAuthor(AuthorDTORequest authorDTORequest) {
        return modelMapper.map(authorDTORequest, Author.class);
    }

    public User convertToUser(UserDTORequest userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTOResponse convertToUserDTOResponse(User user) {
        return modelMapper.map(user, UserDTOResponse.class);
    }

//    public Book convertToBook(BookDTOCreateWithExistingAuthorRequest bookDTOCreateWithExistingAuthorRequest) {
//        Book book = new Book();
//
//        book.setTitle(bookDTOCreateWithExistingAuthorRequest.getTitle());
//        book.setPublicationDate(bookDTOCreateWithExistingAuthorRequest.getPublicationDate());
//        book.setIsbn(bookDTOCreateWithExistingAuthorRequest.getIsbn());
//        book.setTotalQuantity(bookDTOCreateWithExistingAuthorRequest.getQuantity());
//
//        List<Author> authors = new ArrayList<>();
//        for (Long id : bookDTOCreateWithExistingAuthorRequest.getAuthorIds()) {
//            authors.add(authorService.findById(id).g);
//        }
//        book.setAuthors(authors);
//
//        return book;
//    }
}
