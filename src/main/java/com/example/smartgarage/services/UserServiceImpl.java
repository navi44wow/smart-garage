package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.dtos.UserDto;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.UserRoleEntity;
import com.example.smartgarage.models.enums.UserRole;
import com.example.smartgarage.models.service_models.UserServiceModel;
import com.example.smartgarage.repositories.UserRepository;
import com.example.smartgarage.repositories.UserRoleRepository;
import com.example.smartgarage.services.SmartGarageUserService;
import com.example.smartgarage.services.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final UserRoleRepository userRoleRepository;

    private final SmartGarageUserService smartGarageUserService;

    private final Gson gson;

    private final Resource usersFile;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper,
                           UserRoleRepository userRoleRepository, SmartGarageUserService smartGarageUserService,
                           Gson gson, @Value("classpath:init/users.json") Resource usersFile) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.smartGarageUserService = smartGarageUserService;
        this.gson = gson;
        this.usersFile = usersFile;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAllUsers();
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("User with name ", username, " was not found!"));
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id ", id.toString(), " was not found!"));
    }

    @Override
    public void createUser(UserServiceModel userServiceModel) {
        User user = modelMapper.map(userServiceModel, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRole.CUSTOMER).orElseThrow(
                () -> new IllegalStateException("CUSTOMER role not found. Please seed the roles.")
        );

        user.addRole(userRole);

        if (!userServiceModel.getPassword().equals(userServiceModel.getConfirmPassword())){
            throw new IllegalArgumentException("Password is not confirmed properly!");
        }

        if (alreadyExistsUser(user.getUsername(), user.getPhoneNumber(), user.getEmail())) {
            throw new EntityDuplicateException("User", "username", user.getUsername());
        }
        user = userRepository.save(user);

        UserDetails userDetails = smartGarageUserService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                user.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void seedUsers() {
        if (userRepository.count() == 0) {
            try {
                UserRoleEntity employeeRole = userRoleRepository.findByRole(UserRole.EMPLOYEE).orElseThrow();
                User[] usersEntities =
                        gson.fromJson(Files.readString(Path.of(usersFile.getURI())), User[].class);

                Arrays.stream(usersEntities)
                        .forEach(u -> {
                            u.setPassword(passwordEncoder.encode(u.getPassword()));
                        });

                Arrays.stream(usersEntities)
                        .forEach(u -> {
                            u.setRoles(List.of(employeeRole));
                        });

                Arrays.stream(usersEntities)
                        .forEach(userRepository::save);
            } catch (Exception e) {
                throw new IllegalStateException("Cannot seed users");
            }
        }
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("User with email ", email, " was not found!"));
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new EntityNotFoundException("User with phone number ", phoneNumber, " was not found!"));
    }

    @Override
    public User updateUser(User updated, UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())){
            throw new IllegalArgumentException("Password is not confirmed properly!");
        }
        updated.setPassword(passwordEncoder.encode(userDto.getPassword()));
        updated.setPhoneNumber(userDto.getPhoneNumber());
        updated.setEmail(userDto.getEmail());
        userRepository.save(updated);
        return updated;
    }

    @Override
    public User delete(User existingUser) {
        userRepository.delete(existingUser);
        return existingUser;
    }

    private boolean alreadyExistsUser(String username, String phoneNumber, String email) {
        boolean exists = false;
        try {
            if (userRepository.findByUsername(username).isPresent()) {
                throw new EntityDuplicateException("User", "username", username);
            } else if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
                throw new EntityDuplicateException("User", "phone number", phoneNumber);
            } else if (userRepository.findByEmail(email).isPresent()) {
                throw new EntityDuplicateException("User", "email", email);
            }
        } catch (EntityDuplicateException e) {
            exists = true;
        }
        return exists;
    }
}
