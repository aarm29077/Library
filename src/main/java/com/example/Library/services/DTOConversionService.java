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

    @Autowired
    public DTOConversionService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public BookDTOResponseAllInfo convertToBookDTOResponse(Book book) {
        BookDTOResponseAllInfo bookDTOResponse = new BookDTOResponseAllInfo();

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
