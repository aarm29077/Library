package com.example.Library.service.roles;

import com.example.Library.models.roles.Role;
import com.example.Library.repositories.roles.RoleRepository;
import com.example.Library.util.roles.ROLE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Role GETorSAVE(ROLE input_role) {
        Optional<Role> roleByRoleName = findRoleByRoleName(input_role);
        if (roleByRoleName.isEmpty()) {
            return saveRole(input_role);
        }
        return roleByRoleName.get();
    }

    @Transactional
    public Role updateRole(Role role){
        return roleRepository.save(role);
    }

    private Optional<Role> findRoleByRoleName(ROLE role) {
        return roleRepository.findByRoleName(role);
    }

    @Transactional
    private Role saveRole(ROLE input_role) {
        Role creatingRole = new Role();
        creatingRole.setRoleName(input_role);
        return roleRepository.save(creatingRole);
    }
}
