package com.example.Library.repositories.roles;

import com.example.Library.models.roles.Role;
import com.example.Library.util.roles.ROLE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(ROLE roleName);
}
