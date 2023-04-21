package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.services.contracts.CarServizService;
import com.example.smartgarage.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private final CarServizService carServizService;

    public HomeController(CarServizService carServizService) {
        this.carServizService = carServizService;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("allServices", carServizService.getAll());
        return "index";
    }

//    @PostMapping("/forgot_password")
//    public String forgotPassword(@RequestParam String email){
//
//    }
}
