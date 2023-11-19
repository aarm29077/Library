package com.example.Library.Repositories.forRoles;

import com.example.Library.Models.forRoles.Role;
import com.example.Library.Util.roles.ROLE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(ROLE roleName);
}
