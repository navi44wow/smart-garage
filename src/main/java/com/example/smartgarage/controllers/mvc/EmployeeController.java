package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.contracts.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

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