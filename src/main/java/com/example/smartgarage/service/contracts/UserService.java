package com.example.smartgarage.service.contracts;

import com.example.smartgarage.models.dtos.UserDto;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.service_models.UserServiceModel;

import java.util.List;

public interface UserService {
    List<User> getAll();


    User getByUsername(String username);

    User getById(Long id);

    void createUser(UserServiceModel userServiceModel);

    void seedUsers();

    User getByEmail(String email);

    User getByPhoneNumber(String phoneNumber);

    User updateUser(User user, UserDto userDto);

    User delete(User existingUser);
}
