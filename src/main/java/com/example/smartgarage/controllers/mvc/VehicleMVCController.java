package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.dtos.VehicleFilterDto;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.VehicleMapper;
import com.example.smartgarage.services.contracts.UserService;
import com.example.smartgarage.services.contracts.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vehicles")
public class VehicleMVCController {

    private final VehicleService vehicleService;
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final VehicleMapper vehicleMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleMVCController(VehicleService vehicleService, UserService userService, AuthenticationHelper authenticationHelper, VehicleMapper vehicleMapper, ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.vehicleMapper = vehicleMapper;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String getAllVehicles(Model model) {
        List<Vehicle> vehiclesList = vehicleService.getAll();
        model.addAttribute("vehicles", vehiclesList);

        VehicleFilterDto filterDTO = new VehicleFilterDto();
        model.addAttribute("filterDTO", filterDTO);

        return "vehicles";
    }

    @GetMapping("/new")
    public String createNewVehicle(Model model) {
        UserViewModel user = userService.getByUsername("ivan91");
//        try {
//            user = authenticationHelper.tryGetUser(headers);
//        } catch (AuthorizationException e) {
//            return "redirect:/auth/login";
//        }
        model.addAttribute("vehicleDto", new VehicleDto());
        model.addAttribute("user", user);
        return "vehicle-new";
    }

    @PostMapping("/new")
    public String createNewVehicle(@Valid @ModelAttribute("vehicleDto") VehicleDto vehicleDto, BindingResult bindingResult, Model model, HttpSession httpSession) {
        //User user;
        //UserViewModel user = userService.getByUsername("ivan91");
        try {
            User user = userService.getById(UUID.fromString("86879362-de14-4427-b014-d8d315e7b8d7"));
            //User user2= user;
            Vehicle vehicle = vehicleMapper.createDtoToObject(vehicleDto, user);
            vehicleService.save(vehicle);
            return "redirect:/vehicles";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }


}
