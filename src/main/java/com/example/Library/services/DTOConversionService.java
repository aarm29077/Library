package com.example.Library.services;

import com.example.Library.dto.*;
import com.example.Library.models.Author;
import com.example.Library.models.Book;
import com.example.Library.models.BookStock;
import com.example.Library.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DTOConversionService {

    private final ModelMapper modelMapper;

    @Autowired
    public DTOConversionService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public BookDTOResponse convertToBookDTOResponse(Book book) {
        return modelMapper.map(book, BookDTOResponse.class);

    }

    public BookDTOResponseAllInfo convertToBookDTOResponseAllInfo(Book book) {
        BookDTOResponseAllInfo bookDTOResponse = new BookDTOResponseAllInfo();

        List<AuthorDTOResponse> authorDTOResponse = book.getAuthors().stream().map(this::convertToAuthorDTOResponse).collect(Collectors.toList());

        bookDTOResponse.setId(book.getId());
        bookDTOResponse.setTitle(book.getTitle());
        bookDTOResponse.setPublicationDate(book.getPublicationDate());
        bookDTOResponse.setIsbn(book.getIsbn());
        bookDTOResponse.setCurrentQuantity(book.getBookStock().getCurrentQuantity());
        bookDTOResponse.setTotalQuantity(book.getBookStock().getTotalQuantity());

        bookDTOResponse.setAuthors(authorDTOResponse);

        return bookDTOResponse;
    }

    public BookDTOResponseWithAuthors convertToBookDTOResponseWithAuthors(Book book) {
        BookDTOResponseWithAuthors bookDTOResponseWithAuthors = new BookDTOResponseWithAuthors();

        List<AuthorDTOResponse> authorDTOResponse = book.getAuthors().stream().map(this::convertToAuthorDTOResponse).collect(Collectors.toList());

        bookDTOResponseWithAuthors.setId(book.getId());
        bookDTOResponseWithAuthors.setTitle(book.getTitle());
        bookDTOResponseWithAuthors.setPublicationDate(book.getPublicationDate());
        bookDTOResponseWithAuthors.setIsbn(book.getIsbn());

        bookDTOResponseWithAuthors.setAuthors(authorDTOResponse);

        return bookDTOResponseWithAuthors;
    }

    public BookStockDTOResponse convertToBookStockDTOResponse(BookStock bookStock) {
        return modelMapper.map(bookStock, BookStockDTOResponse.class);
    }

    public AuthorDTOResponse convertToAuthorDTOResponse(Author author) {
        return modelMapper.map(author, AuthorDTOResponse.class);
    }

    public Book convertToBook(BookDTORequest book1) {
        Book book = new Book();

        book.setTitle(book1.getTitle());
        book.setPublicationDate(book1.getPublicationDate());
        book.setIsbn(book1.getIsbn());

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


}
