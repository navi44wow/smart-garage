package com.example.smartgarage.models.view_models;

import com.example.smartgarage.models.entities.UserRoleEntity;
import com.example.smartgarage.models.entities.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserViewModel {

    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;

    private List<UserRoleEntity> roles;
    private List<Vehicle> vehicles;
}
