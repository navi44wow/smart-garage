package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.dtos.GenerateUserDto;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.filter_options.UserFilterOptions;
import com.example.smartgarage.models.service_models.UserServiceModel;
import com.example.smartgarage.models.view_models.UserViewModel;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserViewModel> getAll();

    List<String> checkIfExist(String username, String phoneNumber, String email);
    UserViewModel getByUsername(String username);

    User getById(UUID id);

    void createUser(UserServiceModel userServiceModel);

    void seedUsers();

    UserViewModel getByEmail(String email);

    UserViewModel getByPhoneNumber(String phoneNumber);

    UserViewModel updateUser(UserServiceModel user, String username);

    UserViewModel delete(UserServiceModel userViewModel);

    void checkPassword(String password);

    void generateUser(GenerateUserDto generateUserDto);

    void prepareForPasswordReset(UserServiceModel userServiceModel);

    void updatePassword(UserServiceModel user);

    List<UserViewModel> get(UserFilterOptions userFilterOptions);
}
