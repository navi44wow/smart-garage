package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.exceptions.NotValidPasswordException;
import com.example.smartgarage.exceptions.PasswordConfirmationException;
import com.example.smartgarage.models.dtos.GenerateUserDto;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Visit;
import com.example.smartgarage.models.service_models.UserServiceModel;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.contracts.UserService;
import com.example.smartgarage.services.contracts.VisitService;
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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.smartgarage.controllers.mvc.UserMVCController.generateRandomString;
import static com.example.smartgarage.controllers.mvc.UserMVCController.sendEmail;

@Controller
public class CustomerController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    private final VisitService visitService;

    public CustomerController(UserService userService, ModelMapper modelMapper, VisitService visitService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.visitService = visitService;
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

    @GetMapping("/customer/{username}/customer-visits")
    public String getAll(@PathVariable("username") String username, Model model) {
        UserViewModel user = userService.getByUsername(username);
        List<Visit> visits = visitService.getAll().stream()
                .filter(visit -> visit.getVehicle().getUser().getUsername().equals(user.getUsername()))
                .collect(Collectors.toList());
        model.addAttribute("visits", visits);
        model.addAttribute("username", user.getUsername());
        return "customer-visits";
    }

    private UserViewModel getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return userService.getByUsername(authentication.getName());
    }

}
