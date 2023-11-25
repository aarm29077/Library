package com.example.Library.service.conversion;

import com.example.Library.dto.author.AuthorDTORequest;
import com.example.Library.dto.author.AuthorDTOResponse;
import com.example.Library.dto.book.BookDTORequest;
import com.example.Library.dto.book.BookDTOResponse;
import com.example.Library.dto.book.BookDTOResponseAllInfo;
import com.example.Library.dto.book.BookDTOResponseWithAuthors;
import com.example.Library.dto.bookStock.BookStockDTOResponse;
import com.example.Library.dto.auth.AuthenticationRequest;
import com.example.Library.dto.user.UserDTORequest;
import com.example.Library.dto.user.UserDTOResponse;
import com.example.Library.models.books.Author;
import com.example.Library.models.books.Book;
import com.example.Library.models.books.BookStock;
import com.example.Library.models.users.User;
import com.example.Library.models.users.UserCredentials;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DTOConversionService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public DTOConversionService(ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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

    public UserCredentials authenticationRequestTOUserCredentials(AuthenticationRequest authenticationRequest){
        UserCredentials credentials = new UserCredentials();
        credentials.setUsername(authenticationRequest.getUsername());
        credentials.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
        return credentials;
    }

}
