package com.example.Library.Dto.book;

import com.example.Library.Dto.author.AuthorDTOResponse;
import com.example.Library.Util.customAnnotations.ValidString;
import com.example.Library.Util.customAnnotations.YearFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;

import java.util.List;

public class BookDTOResponseWithAuthors {
    private @Getter @Setter Long id;

    @Size(min = 2, max = 30, message = "The book's title should be between 2 and 30 characters")
    @NotBlank(message = "The book's title should not be empty")
    @ValidString(message = "The given title is not valid")
    private @Getter @Setter String title;

    @YearFormat()
    @NotBlank(message = "publicationDate should not be empty")
    private @Getter @Setter String publicationDate;

    @ISBN(message = "It isn't ISBN format")
    @ValidString(message = "The given ISBN is not valid")
    @NotBlank(message = "The ISBN should not be empty")
    private @Getter @Setter String isbn;

    @NotEmpty(message = "Authors should not be empty")
    private @Getter @Setter List<AuthorDTOResponse> authors;

}
