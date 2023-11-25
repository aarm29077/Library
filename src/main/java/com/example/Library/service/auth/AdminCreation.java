package com.example.Library.service.auth;

import com.example.Library.models.roles.Role;
import com.example.Library.models.users.User;
import com.example.Library.models.users.UserCredentials;
import com.example.Library.security.UserDetailsImpl;
import com.example.Library.service.JwtService;
import com.example.Library.service.roles.RoleService;
import com.example.Library.service.users.UserCredentialsService;
import com.example.Library.service.users.UserService;
import com.example.Library.token.Token;
import com.example.Library.token.TokenService;
import com.example.Library.token.TokenType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.Library.util.roles.ROLE.ROLE_ADMIN;

@Component
@RequiredArgsConstructor
public class AdminCreation {

    private final UserService userService;
    private final UserCredentialsService userCredentialsService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final TokenService tokenService;

    @Value("${main_admin_username}")
    private String adminUsername;

    @Value("${main_admin_password}")
    private String adminPassword;

    @Value("${main_admin_mail}")
    private String adminMail;

    @PostConstruct
    @Transactional
    public void createDefaultAdminIfNotExists() throws ParseException {

        if (userCredentialsService.findUserByUsername(adminUsername).isPresent()) {
            return;
        }

        UserCredentials userCredentials = UserCredentials.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .build();

        Role role_admin = roleService.GETorSAVE(ROLE_ADMIN);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("1990-10-10");

        User user = User.builder()
                .name("Karo")
                .surname("Karapetyan")
                .dateOfBirth(date)
                .email(adminMail)
                .credentials(userCredentials)
                .role(role_admin)
                .build();

        userCredentials.setUser(user);

        User savedUser = userService.createUser(user);
        userCredentialsService.createUserCredentials(userCredentials);

        String jwtToken = jwtService.generateToken(new UserDetailsImpl(user));

        saveUserToken(savedUser, jwtToken);

    }

    @Transactional
    private void saveUserToken(User user, String jwtToken) {
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
