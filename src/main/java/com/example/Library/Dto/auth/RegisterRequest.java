package com.example.Library.Dto.auth;

import com.example.Library.Util.customAnnotations.AgeOver12;
import com.example.Library.Util.customAnnotations.ValidString;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Size(min = 2, max = 30, message = "The user's name should be between 2 and 30 characters")
    @NotBlank(message = "The user's name should not be empty")
    @ValidString(message = "The given user's name is not valid")
    private @Getter @Setter String name;

    @Size(min = 2, max = 30, message = "The user's surname should be between 2 and 30 characters")
    @NotBlank(message = "The user's surname should not be empty")
    @ValidString(message = "The given user's surname is not valid")
    private @Getter @Setter String surname;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @AgeOver12
    private @Getter @Setter Date dateOfBirth;

    @Email(message = "Please provide a valid email address")
    @ValidString(message = "The given user's email is not valid")
    private @Getter @Setter String email;

    private @Getter @Setter AuthenticationRequest credentials;
}
