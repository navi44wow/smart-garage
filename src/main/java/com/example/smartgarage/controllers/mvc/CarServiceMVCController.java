package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.entities.CarService;
import com.example.smartgarage.services.contracts.CarServizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/services")
public class CarServiceMVCController {

    private final CarServizService carServizService;

    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public CarServiceMVCController(CarServizService carServizService, AuthenticationHelper authenticationHelper) {
        this.carServizService = carServizService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping()
    public String getAll(Model model) {
        List<CarService> services = carServizService.getAll();
        model.addAttribute("all", services);
        return "services";
    }

    @GetMapping("/service-new")
    public String createNewService(Model model) {
  //TODO authentication and logic
        return "service-new";
    }
}
