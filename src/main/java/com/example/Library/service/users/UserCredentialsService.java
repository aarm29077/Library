package com.example.Library.service.users;

import com.example.Library.models.users.User;
import com.example.Library.models.users.UserCredentials;
import com.example.Library.repositories.users.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserCredentialsService {
    private final UserCredentialsRepository userCredentialsRepository;

    @Autowired
    public UserCredentialsService(UserCredentialsRepository userCredentialsRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
    }

    public Optional<User> findUserByUsername(String username) {
        Optional<UserCredentials> byUsername = userCredentialsRepository.findByUsername(username);
        if (byUsername.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(byUsername.get().getUser());
    }

    @Transactional //this will be automatically when we create the user
    public boolean createUserCredentials(UserCredentials credentials) {
        if (userCredentialsRepository.findByUsername(credentials.getUsername()).isPresent()) return false;

        userCredentialsRepository.save(credentials);
        return true;
    }
    /*
    @Transactional
    public boolean updateUserCredentials(UserCredentials credentials) {
        if (userCredentialsRepository.findByUsername(credentials.getUsername()).isPresent()) return false;

        userCredentialsRepository.save(credentials);
        return true;
    }*/
}
