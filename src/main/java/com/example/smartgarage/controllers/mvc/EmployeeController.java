package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.exceptions.NotValidPasswordException;
import com.example.smartgarage.exceptions.PasswordConfirmationException;
import com.example.smartgarage.models.dtos.GenerateUserDto;
import com.example.smartgarage.models.dtos.UserDto;
import com.example.smartgarage.models.service_models.UserServiceModel;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final AuthenticationManager authenticationManager;

    private final ModelMapper modelMapper;
    private final UserService userService;

    public EmployeeController(AuthenticationManager authenticationManager, ModelMapper modelMapper, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    private UserViewModel getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return userService.getByUsername(authentication.getName());
    }

    @ModelAttribute("userDto")
    public UserDto createUserModel() {
        return new UserDto();
    }

    @ModelAttribute("generateUserDto")
    public GenerateUserDto createGenerateUserModel() {
        return new GenerateUserDto();
    }


    @ModelAttribute("loggedInUser")
    public UserViewModel loggedInUser() {
        return getLoggedInUser();
    }


    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping()
    public String home() {
            return "employee-dashboard";
    }


    @GetMapping("/resetPassword/{username}")
    public String getResetPassword(@PathVariable String username, Model model) {
        model.addAttribute("username", username);
        return "reset_password";
    }

    @PostMapping("/resetPassword/{username}")
    public String handleForgotPassword(@PathVariable String username, GenerateUserDto generateUserDto,
                                       Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("generateUserDto", generateUserDto);
            model.addAttribute("org.springframework.validation.BindingResult.generateUserDto", bindingResult);
        }
        generateUserDto.setUsername(username);
        UserServiceModel userServiceModel = modelMapper.map(generateUserDto, UserServiceModel.class);
        try {
            userService.updatePassword(userServiceModel);
        } catch (NotValidPasswordException e) {
            model.addAttribute("generateUserDto", generateUserDto);
            model.addAttribute("invalidPassword", true);
            model.addAttribute("exceptionPassMessage", e.getMessage());
            return "reset_password";
        } catch (PasswordConfirmationException e) {
            model.addAttribute("generateUserDto", generateUserDto);
            model.addAttribute("notConfirmedPassword", true);
            model.addAttribute("confirmationExcMessage", e.getMessage());
            return "reset_password";
        } catch (EntityNotFoundException e) {
            model.addAttribute("generateUserDto", generateUserDto);
            model.addAttribute("username", username);
            model.addAttribute("userNotFound", true);
            return "reset_password";
        }

        return "redirect:/users/login";
    }
}
