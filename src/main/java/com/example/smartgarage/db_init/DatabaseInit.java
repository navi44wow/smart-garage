package com.example.smartgarage.db_init;

import com.example.smartgarage.services.contracts.UserRoleService;
import com.example.smartgarage.services.contracts.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements CommandLineRunner {
    private final UserService userService;
    private final UserRoleService userRoleService;

    public DatabaseInit(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }


    @Override
    public void run(String... args) throws Exception {
        userRoleService.seedRoles();
        userService.seedUsers();
    }
}
