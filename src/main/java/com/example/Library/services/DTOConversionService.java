package com.example.Library.services;

import com.example.Library.dto.AuthorDTORequest;
import com.example.Library.dto.AuthorDTOResponse;
import com.example.Library.dto.BookDTORequest;
import com.example.Library.dto.BookDTOResponse;
import com.example.Library.models.Author;
import com.example.Library.models.Book;
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
        BookDTOResponse bookDTOResponse = new BookDTOResponse();

        List<AuthorDTOResponse> authorDTOResponse = book.getAuthors().stream().map(this::convertToAuthorDTOResponse).collect(Collectors.toList());

        bookDTOResponse.setTitle(book.getTitle());
        bookDTOResponse.setPublicationDate(book.getPublicationDate());
        bookDTOResponse.setIsbn(book.getIsbn());
        bookDTOResponse.setCurrentQuantity(book.getCurrentQuantity());
        bookDTOResponse.setTotalQuantity(book.getTotalQuantity());
        bookDTOResponse.setAuthors(authorDTOResponse);

        return bookDTOResponse;
    }

    public AuthorDTOResponse convertToAuthorDTOResponse(Author author) {
        return modelMapper.map(author, AuthorDTOResponse.class);
    }

    public Book convertToBook(BookDTORequest book1) {
        Book book = new Book();

        List<Author> authors = book1.getAuthors().stream().map(this::convertToAuthor).collect(Collectors.toList());

        book.setTitle(book1.getTitle());
        book.setPublicationDate(book1.getPublicationDate());
        book.setIsbn(book1.getIsbn());
        book.setCurrentQuantity(book1.getQuantity());
        book.setTotalQuantity(book1.getQuantity());
        book.setAuthors(authors);

        return book;
    }

    public Author convertToAuthor(AuthorDTORequest authorDTORequest) {
        return modelMapper.map(authorDTORequest, Author.class);
    }
}
