package com.example.Library.dto;

import com.example.Library.util.customAnnotations.ValidString;
import com.example.Library.util.customAnnotations.YearFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;

import java.util.List;

public class BookDTOResponseAllInfo {

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

    @Min(value = 0, message = "Minimum current quantity should be 0")
    private @Getter @Setter int currentQuantity;

    @Min(value = 1, message = "Minimum total quantity should be 1")
    private @Getter @Setter int totalQuantity;

    @NotEmpty(message = "Authors should not be empty")
    private @Getter @Setter List<AuthorDTOResponse> authors;

}
