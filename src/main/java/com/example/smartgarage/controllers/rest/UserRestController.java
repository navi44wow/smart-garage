package com.example.smartgarage.controllers.rest;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.UserDto;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.service_models.UserServiceModel;

import com.example.smartgarage.services.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;


import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AuthenticationHelper authenticationHelper;

    public UserRestController(UserService userService, ModelMapper modelMapper, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authenticationHelper = authenticationHelper;
    }


    @GetMapping("/all")
    public List<User> getAllUsers(@RequestHeader("Authorization") HttpHeaders headers){
        try {
            authenticationHelper.checkAuthorization(headers);
            return userService.getAll();
        } catch (AuthorizationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }


    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username, @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return userService.getByUsername(username);
        } catch (AuthorizationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email, @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return userService.getByEmail(email);
        } catch (AuthorizationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    public User getUserByPhoneNumber(@PathVariable String phoneNumber, @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return userService.getByPhoneNumber(phoneNumber);
        } catch (AuthorizationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PostMapping("/create")
    public User createUser(@RequestHeader("Authorization") HttpHeaders headers,
                           @Valid @RequestBody UserDto userDto){
        UserServiceModel userServiceModel;
        try {
            authenticationHelper.checkAuthorization(headers);
            userServiceModel = modelMapper.map(userDto, UserServiceModel.class);
            userService.createUser(userServiceModel);
            return userService.getByUsername(userServiceModel.getUsername());
        } catch (AuthorizationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/update/{username}")
    public User updateUser(@PathVariable String username, @RequestHeader HttpHeaders headers,
                           @Valid @RequestBody UserDto userDto){
        User existingUser;
        try {
            authenticationHelper.checkAuthorization(headers);
            existingUser = userService.getByUsername(username);
            return userService.updateUser(existingUser, userDto);
        } catch (AuthorizationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{username}")
    public User deleteUser(@PathVariable String username, @RequestHeader HttpHeaders headers){
        User existingUser;
        try {
            authenticationHelper.checkAuthorization(headers);
            existingUser = userService.getByUsername(username);
            return userService.delete(existingUser);
        } catch (AuthorizationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }



}
