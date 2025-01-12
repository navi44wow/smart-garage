package com.example.smartgarage.controllers.rest;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.exceptions.NotValidPasswordException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.UserDto;
import com.example.smartgarage.models.dtos.UserFilterDto;
import com.example.smartgarage.models.filter_options.UserFilterOptions;
import com.example.smartgarage.models.service_models.UserServiceModel;

import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
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


//    @GetMapping("/all")
//    public List<UserViewModel> getAllUsers(UserFilterDto userFilterDto,
//                                           @RequestHeader("Authorization") HttpHeaders headers) {
//
//        UserFilterOptions userFilterOptions;
//        try {
//            authenticationHelper.checkAuthorization(headers);
//            userFilterOptions = new UserFilterOptions(
//                    userFilterDto.getUsername(),
//                    userFilterDto.getEmail(),
//                    userFilterDto.getPhoneNumber(),
//                    userFilterDto.getVehicleVin(),
//                    userFilterDto.getVehicleModel(),
//                    userFilterDto.getVehicleBrand(),
//                    userFilterDto.getVisitFirstDate(),
//                    userFilterDto.getVisitLastDate(),
//                    userFilterDto.getVisitDate(),
//                    userFilterDto.getSortBy(),
//                    userFilterDto.getSortOrder()
//            );
//        } catch (AuthorizationException e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
//        }
//
//
//        return userService.get(userFilterOptions);
//    }


    @GetMapping("/username/{username}")
    public UserViewModel getUserByUsername(@PathVariable String username, @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return userService.getByUsername(username);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @GetMapping("/email/{email}")
    public UserViewModel getUserByEmail(@PathVariable String email, @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return userService.getByEmail(email);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    public UserViewModel getUserByPhoneNumber(@PathVariable String phoneNumber, @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return userService.getByPhoneNumber(phoneNumber);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PostMapping("/create")
    public UserViewModel createUser(@RequestHeader("Authorization") HttpHeaders headers,
                                    @Valid @RequestBody UserDto userDto) {
        UserServiceModel userServiceModel;
        try {
            authenticationHelper.checkAuthorization(headers);
            userServiceModel = modelMapper.map(userDto, UserServiceModel.class);
            userService.createUser(userServiceModel);
            return userService.getByUsername(userServiceModel.getUsername());
        } catch (NotValidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/update/{username}")
    public UserViewModel updateUser(@PathVariable String username, @RequestHeader HttpHeaders headers,
                                    @Valid @RequestBody UserDto userDto) {

        UserServiceModel userServiceModel;
        try {
            authenticationHelper.checkAuthorization(headers);
            userServiceModel = modelMapper.map(userDto, UserServiceModel.class);
            return userService.updateUser(userServiceModel, username);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{username}")
    public UserViewModel deleteUser(@PathVariable String username, @RequestHeader HttpHeaders headers) {
        UserServiceModel existingUser;
        try {
            authenticationHelper.checkAuthorization(headers);
            UserViewModel userViewModel = userService.getByUsername(username);
            existingUser = modelMapper.map(userViewModel, UserServiceModel.class);
            return userService.delete(existingUser);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }


}
