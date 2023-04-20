package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.exceptions.NotValidPasswordException;
import com.example.smartgarage.exceptions.PasswordConfirmationException;
import com.example.smartgarage.models.dtos.GenerateUserDto;
import com.example.smartgarage.models.service_models.UserServiceModel;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.example.smartgarage.controllers.mvc.UserMVCController.generateRandomString;
import static com.example.smartgarage.controllers.mvc.UserMVCController.sendEmail;

@Controller
public class CustomerController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public CustomerController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("loggedInUser")
    public UserViewModel loggedInUser() {
        return getLoggedInUser();
    }

    @ModelAttribute("generateUserDto")
    public GenerateUserDto createGenerateUserModel() {
        return new GenerateUserDto();
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/customer")
    public String home() {
        return "customer-dashboard";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgot_password";
    }

    @PostMapping("/forgotPassword")
    public String sendEmailForForgottenPassword(@Valid GenerateUserDto generateUserDto, BindingResult bindingResult,
                                                Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("generateUserDto", generateUserDto);
            model.addAttribute("org.springframework.validation.BindingResult.generateUserDto", bindingResult);

            return "forgot_password";
        }

        try {
            generateUserDto.setPassword(generateRandomString(8));
            UserViewModel userViewModel = userService.getByEmail(generateUserDto.getEmail());
            generateUserDto.setUsername(userViewModel.getUsername());
            UserServiceModel userServiceModel = modelMapper.map(generateUserDto, UserServiceModel.class);
            userService.prepareForPasswordReset(userServiceModel);
            sendEmail(userServiceModel);
        } catch (EntityNotFoundException e) {
            model.addAttribute("generateUserDto", generateUserDto);
            model.addAttribute("userNotFound", true);
            model.addAttribute("exceptionNotFound", e.getMessage());
            return "forgot_password";
        }


        return "redirect:/users/login";
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


    private UserViewModel getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return userService.getByUsername(authentication.getName());
    }




}
