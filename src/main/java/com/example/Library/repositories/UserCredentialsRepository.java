package com.example.Library.repositories;

import com.example.Library.models.User;
import com.example.Library.models.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    Optional<User> findUserByUsername(String username);
}
