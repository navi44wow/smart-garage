package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.VehicleFilterDto;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.services.contracts.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vehicles")
public class VehicleMVCController {


    private final VehicleService vehicleService;
    private final AuthenticationHelper authenticationHelper;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleMVCController(VehicleService vehicleService, AuthenticationHelper authenticationHelper, ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.authenticationHelper = authenticationHelper;
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

}
