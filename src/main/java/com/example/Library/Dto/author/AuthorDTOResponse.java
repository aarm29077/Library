package com.example.Library.Dto.author;

import com.example.Library.Util.customAnnotations.ValidString;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class AuthorDTOResponse {

    private @Getter @Setter Long id;

    @NotBlank(message = "The name should not be empty")
    @Size(min = 2, max = 30, message = "The author's name should be between 2 and 30 characters")
    @ValidString(message = "The given author's name is not valid")
    private @Getter @Setter String name;

    @NotBlank(message = "The surname should not be empty")
    @Size(min = 2, max = 30, message = "The author's surname should be between 2 and 30 characters")
    @ValidString(message = "The given author's surname is not valid")
    private @Getter @Setter String surname;

}
