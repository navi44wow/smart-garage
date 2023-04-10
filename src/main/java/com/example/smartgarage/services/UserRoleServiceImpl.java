package com.example.smartgarage.services;

import com.example.smartgarage.models.entities.UserRoleEntity;
import com.example.smartgarage.models.enums.UserRole;
import com.example.smartgarage.repositories.UserRoleRepository;
import com.example.smartgarage.services.contracts.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void seedRoles() {
        if(userRoleRepository.count() == 0){
            UserRoleEntity employeeRole = new UserRoleEntity().setRole(UserRole.EMPLOYEE);
            UserRoleEntity customerRole = new UserRoleEntity().setRole(UserRole.CUSTOMER);

            userRoleRepository.saveAll(List.of(employeeRole, customerRole));
        }
    }
}
