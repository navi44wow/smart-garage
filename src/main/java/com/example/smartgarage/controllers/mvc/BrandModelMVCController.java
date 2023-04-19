package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.models.dtos.ModelFilterDto;
import org.springframework.ui.Model;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.services.VehicleMapper;
import com.example.smartgarage.services.contracts.BrandService;
import com.example.smartgarage.services.contracts.ModelService;
import com.example.smartgarage.services.contracts.UserService;
import com.example.smartgarage.services.contracts.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/models")
public class BrandModelMVCController {

    private final VehicleService vehicleService;
    private final BrandService brandService;
    private final ModelService modelService;
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final VehicleMapper vehicleMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public BrandModelMVCController(VehicleService vehicleService, BrandService brandService, ModelService modelService, UserService userService, AuthenticationHelper authenticationHelper, VehicleMapper vehicleMapper, ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.brandService = brandService;
        this.modelService = modelService;
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.vehicleMapper = vehicleMapper;
        this.modelMapper = modelMapper;
    }


    @GetMapping()
    public String getAllBrandsModels(Model model) {
        List<com.example.smartgarage.models.entities.Model> modelList = modelService.getAll();
        model.addAttribute("models", modelList);

        ModelFilterDto modelFilterDto = new ModelFilterDto();
        model.addAttribute("modelFilterDto", modelFilterDto);

        return "models";
    }


}
