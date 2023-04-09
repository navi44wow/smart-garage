package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.dtos.UserDto;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.service_models.UserServiceModel;
import com.example.smartgarage.models.view_models.UserViewModel;

import java.util.List;

public interface UserService {
    List<UserViewModel> getAll();


    UserViewModel getByUsername(String username);

    User getById(Long id);

    void createUser(UserServiceModel userServiceModel);

    void seedUsers();

    UserViewModel getByEmail(String email);

    UserViewModel getByPhoneNumber(String phoneNumber);

    UserViewModel updateUser(UserServiceModel user, UserDto userDto);

    UserViewModel delete(UserViewModel userViewModel);
}
