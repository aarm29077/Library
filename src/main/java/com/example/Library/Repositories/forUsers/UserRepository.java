package com.example.Library.Repositories.forUsers;

import com.example.Library.Models.forUsers.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<List<User>> findByName(String name);

    Optional<List<User>> findBySurname(String surname);

    Optional<User> findByEmail(String email);
}
