package com.example.Library.service.auth;

import com.example.Library.dto.auth.AuthenticationResponse;
import com.example.Library.dto.auth.AuthenticationRequest;
import com.example.Library.dto.auth.RegisterRequest;
import com.example.Library.models.roles.Role;
import com.example.Library.models.users.User;
import com.example.Library.models.users.UserCredentials;
import com.example.Library.service.JwtService;
import com.example.Library.service.roles.RoleService;
import com.example.Library.security.UserDetailsImpl;
import com.example.Library.service.conversion.DTOConversionService;
import com.example.Library.service.users.UserCredentialsService;
import com.example.Library.service.users.UserService;
import com.example.Library.token.Token;
import com.example.Library.token.TokenService;
import com.example.Library.token.TokenType;
import com.example.Library.util.customExceptions.credentials.UsernameExistsException;
import com.example.Library.util.customExceptions.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.Library.util.roles.ROLE.ROLE_USER;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthenticationService implements TokensManagement {

    private final UserService userService;
    private final DTOConversionService dtoConversionService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserCredentialsService userCredentialsService;
    private final TokenService tokenService;


    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        UserCredentials userCredentials = dtoConversionService.authenticationRequestTOUserCredentials(request.getCredentials());

        if (userCredentialsService.findUserByUsername(userCredentials.getUsername()).isPresent()) {
            throw new UsernameExistsException("This username already exists");
        }

        Role role = roleService.GETorSAVE(ROLE_USER);

        User user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .credentials(userCredentials)
                .role(role)
                .build();

        User savedUser = userService.createUser(user);

        String jwtToken = jwtService.generateToken(new UserDetailsImpl(user));

        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /*
    Will do all job for me and in case the username or the password are not correct so an exception would be thrown */
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        //Ete hasel enq stex uremn User-y authenticated e
        //generate token and send it back

        User user = userCredentialsService.findUserByUsername(
                request.getUsername()
        ).orElseThrow(() -> new UserNotFoundException("User not found"));

        String jwtToken = jwtService.generateToken(new UserDetailsImpl(user));

        revokeAllTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    @Transactional
    public void revokeAllTokens(User user) {
        List<Token> validUserTokens = tokenService.findAllValidTokensByUser(user.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
                    token.setExpired(true);
                    token.setRevoked(true);
                }
        );
        tokenService.saveAllTokens(validUserTokens);
    }

    @Override
    @Transactional
    public void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenService.saveToken(token);
    }
}
