package com.example.smartgarage.models.service_models;

import com.example.smartgarage.exceptions.NotValidPasswordException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String phoneNumber;
}
