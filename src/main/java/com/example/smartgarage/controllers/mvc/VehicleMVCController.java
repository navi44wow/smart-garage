package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.dtos.VehicleFilterDto;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.services.VehicleMapper;
import com.example.smartgarage.services.contracts.ModelService;
import com.example.smartgarage.services.contracts.UserService;
import com.example.smartgarage.services.contracts.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final ModelService modelService;

    private final AuthenticationHelper authenticationHelper;
    private final VehicleMapper vehicleMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleMVCController(VehicleService vehicleService, UserService userService, ModelService modelService, AuthenticationHelper authenticationHelper, VehicleMapper vehicleMapper, ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.modelService = modelService;
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
        model.addAttribute("vehicleDto", new VehicleDto());
        return "vehicle-new";
    }

    @PostMapping("/new")
    public String createNewVehicle(@Valid @ModelAttribute("vehicleDto") VehicleDto vehicleDto, Model model) {
        Vehicle vehicle = vehicleMapper.createDtoToObject(vehicleDto);
        vehicleService.save(vehicle);
        model.addAttribute("vehicle", vehicle);
        return "redirect:/vehicles";
    }

//    @GetMapping("/new")
//    public String createNewVehicle(Model model) {
//        model.addAttribute("vehicleDto", new VehicleDto());
//        return "vehicle-new";
//    }
//
//    @PostMapping("/new")
//    public String createNewVehicle(@Valid @ModelAttribute("vehicleDto") VehicleDto vehicleDto, BindingResult bindingResult, HttpSession httpSession) {
//        Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);
//        vehicleService.save(vehicle);
//        vehicle.setUser(vehicleDto.setUser(vehicleDto.getUser()));
//        vehicle.setModelId(vehicleDto.getModel().longValue());
////        vehicle.setUser(userService.getById(UUID.fromString("157e3e10-a4d8-47dc-be3e-124406cd93fd")));
////        vehicle.setModelId(modelService.getById(17L));
//        return "redirect:/vehicles";
//    }
}
