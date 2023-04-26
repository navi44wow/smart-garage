package com.example.smartgarage;

import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.UserRoleEntity;
import com.example.smartgarage.models.enums.UserRole;
import com.example.smartgarage.models.filter_options.UserFilterOptions;
import com.example.smartgarage.repositories.UserRoleRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Helpers {

    private static UserRoleRepository userRoleRepository;

    public static User createMockAdmin() {
        User mockUser = createMockUser();
        UserRoleEntity employeeRole = userRoleRepository.findByRole(UserRole.EMPLOYEE).orElseThrow();

        mockUser.setRoles(List.of());
        return mockUser;
    }

    public static User createMockUser() {
        var mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUsername("MockUsername");
        mockUser.setPassword("MockPassword");
        mockUser.setLastName("MockLastName");
        mockUser.setFirstName("MockFirstName");
        mockUser.setPhoneNumber("0894053050");
        mockUser.setEmail("mock@user.com");
        return mockUser;
    }

    public static UserFilterOptions createMockFilterOptions() {
        return new UserFilterOptions(
                "name",
                "mock@abv.bg",
                "0000000000",
                "vehicleVin",
                "sort",
                "order",
                LocalDate.parse("2023-03-09"),
                LocalDate.parse("2023-03-02"),
                LocalDate.parse("2023-04-06"),
                "Username",
                "Asc");
    }
}