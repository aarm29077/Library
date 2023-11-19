package com.example.Library.Repositories;

import com.example.Library.Models.BookUser;
import com.example.Library.Models.BookUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookUserRepository extends JpaRepository<BookUser, BookUserId> {
    Optional<BookUser> findByBookIdAndUserId(Long bookId, Long customerId);
}
