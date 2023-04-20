package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.contracts.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;
    public EmployeeController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }


    private UserViewModel getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return userService.getByUsername(authentication.getName());
    }


    @ModelAttribute("loggedInUser")
    public UserViewModel loggedInUser(){
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

@GetMapping("/reset_password")
public String forgotPassword() {
    return "/reset_password";
}

}
