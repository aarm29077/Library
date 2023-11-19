package com.example.Library.Services.auth;

import com.example.Library.Dto.auth.AuthenticationResponse;
import com.example.Library.Dto.auth.AuthenticationRequest;
import com.example.Library.Dto.auth.RegisterRequest;
import com.example.Library.Models.forRoles.Role;
import com.example.Library.Models.forUsers.User;
import com.example.Library.Models.forUsers.UserCredentials;
import com.example.Library.Services.JwtService;
import com.example.Library.Services.forRoles.RoleService;
import com.example.Library.Security.UserDetailsImpl;
import com.example.Library.Services.conversion.DTOConversionService;
import com.example.Library.Services.forUsers.UserCredentialsService;
import com.example.Library.Services.forUsers.UserService;
import com.example.Library.Util.customExceptions.relatedToCredentials.UsernameExistsException;
import com.example.Library.Util.customExceptions.relatedToUser.UserExistsException;
import com.example.Library.Util.customExceptions.relatedToUser.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.Library.Util.roles.ROLE.ROLE_USER;


@Service
@Transactional(readOnly = true)
public class AuthenticationService {

    private final UserService userService;
    private final DTOConversionService dtoConversionService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserCredentialsService userCredentialsService;

    @Autowired
    public AuthenticationService(UserService userService, DTOConversionService dtoConversionService, RoleService roleService, JwtService jwtService, AuthenticationManager authenticationManager, UserCredentialsService userCredentialsService) {
        this.userService = userService;
        this.dtoConversionService = dtoConversionService;
        this.roleService = roleService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userCredentialsService = userCredentialsService;
    }

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

        List<User> userList = role.getUserList();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        userList.add(user);

        boolean isUserCreated = userService.createUser(user);
        if (!isUserCreated) {
            throw new UserExistsException("User with email " + request.getEmail() + " is already exist");
        }
        roleService.updateRole(role);

        String jwtToken = jwtService.generateToken(new UserDetailsImpl(user));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /*
    Will do all job for me and in case the username or the password are not correct so an exception would be thrown */
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
        ).orElseThrow(() -> new UserNotFoundException("Username not found"));

        String jwtToken = jwtService.generateToken(new UserDetailsImpl(user));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
