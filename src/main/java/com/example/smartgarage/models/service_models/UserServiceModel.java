package com.example.smartgarage.models.service_models;

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
    @Size(min = 2, max = 20)
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    @Size(min = 10, max = 10)
    private String phoneNumber;
}
