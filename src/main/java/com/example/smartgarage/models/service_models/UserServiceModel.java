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
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String phoneNumber;

    public void setPassword(String password) throws NotValidPasswordException{
        // check password length
        if (password.length() < 8) {
            throw new NotValidPasswordException("Password must be at least 8 characters long");
        }

        // check for capital letter, digit, and special symbol
        boolean hasCapital = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        if (!hasCapital || !hasDigit || !hasSpecial) {
            throw new NotValidPasswordException("Password must contain at least one capital letter, one digit, and one special symbol");
        }

        // if all checks pass, set the password
        this.password = password;
    }
}
