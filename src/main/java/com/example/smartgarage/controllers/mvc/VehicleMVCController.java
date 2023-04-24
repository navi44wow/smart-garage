package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.dtos.VehicleFilterDto;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.services.VehicleMapper;
import com.example.smartgarage.services.contracts.CarModelService;
import com.example.smartgarage.services.contracts.UserService;
import com.example.smartgarage.services.contracts.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/vehicles")
public class VehicleMVCController {

    private final VehicleService vehicleService;
    private final UserService userService;

    private final CarModelService carModelService;

    private final AuthenticationHelper authenticationHelper;
    private final VehicleMapper vehicleMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleMVCController(VehicleService vehicleService, UserService userService, CarModelService carModelService, AuthenticationHelper authenticationHelper, VehicleMapper vehicleMapper, ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.carModelService = carModelService;
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
    public String createNewVehicle(@Valid @ModelAttribute("vehicleDto") VehicleDto vehicleDto, RedirectAttributes redirectAttributes) {
//        User user = new User();
//        user.setId(vehicleDto.getUser());
//        Vehicle vehicle = vehicleMapper.createDtoToObject(vehicleDto);
//        vehicleService.save(vehicle);
//        model.addAttribute("vehicle", vehicle);

//        User user = new User();
//        user.setId(vehicleDto.getUser());
        Vehicle vehicle = vehicleMapper.createDtoToObject(vehicleDto);
//        Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);
        vehicleService.save(vehicle);
     //   redirectAttributes.addAttribute("id", vehicle.getVehicleId());
        /*
        CarService service = modelMapper.map(serviceDto, CarService.class);
            carServizService.save(service);
            redirectAttributes.addAttribute("id", service.getId());
            return "redirect:/services";
         */
        return "redirect:/vehicles";
    }
}
