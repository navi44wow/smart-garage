package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.entities.UserRoleEntity;
import com.example.smartgarage.models.enums.UserRole;

public interface UserRoleService {
    void seedRoles();

    UserRoleEntity getByUserRole(UserRole employee);

}
