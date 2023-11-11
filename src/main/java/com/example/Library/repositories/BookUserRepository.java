package com.example.Library.repositories;

import com.example.Library.models.BookUser;
import com.example.Library.models.BookUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookUserRepository extends JpaRepository<BookUser, BookUserId> {
    Optional<BookUser> findByBookIdAndUserId(Long bookId, Long customerId);
}
