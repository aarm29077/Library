package com.example.Library.dto.auth;

import com.example.Library.util.customAnnotations.ValidString;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {//AuthenticationRequest

    @Column(name = "username", unique = true)
    @Size(min = 2, max = 30, message = "The username should be between 2 and 30 characters")
    @NotBlank(message = "The username should not be empty")
    @ValidString(message = "The given string is not valid")
    private @Getter @Setter String username;

    @Column(name = "password", unique = true)
    @Size(min = 5, max = 15, message = "The password should be between 5 and 15 characters")
    @NotBlank(message = "The password should not be empty")
    @ValidString(message = "The given string is not valid")
    private @Getter @Setter String password;
}
