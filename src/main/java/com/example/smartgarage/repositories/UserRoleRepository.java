package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.UserRoleEntity;
import com.example.smartgarage.models.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UUID> {
    Optional<UserRoleEntity> findByRole(UserRole user);

}
