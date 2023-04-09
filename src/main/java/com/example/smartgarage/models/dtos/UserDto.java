package com.example.smartgarage.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 2, max = 20)
    private String username;
    @NotBlank(message = "password cannot be empty")
    private String password;
    @NotBlank(message = "Confirm password cannot be empty")
    private String confirmPassword;
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 10, max = 10)
    private String phoneNumber;
}
