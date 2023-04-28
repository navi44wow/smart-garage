package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.dtos.VehicleFilterDto;
import com.example.smartgarage.models.entities.Brand;
import com.example.smartgarage.models.entities.CarModel;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.repositories.CarModelRepository;
import com.example.smartgarage.services.contracts.BrandService;
import com.example.smartgarage.services.contracts.CarModelService;
import com.example.smartgarage.services.contracts.UserService;
import com.example.smartgarage.services.contracts.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/vehicles")
public class VehicleMVCController {

    private final VehicleService vehicleService;
    private final UserService userService;

    private final CarModelService carModelService;

    private final CarModelRepository carModelRepository;

    private final BrandService brandService;

    private final AuthenticationHelper authenticationHelper;

    private final ModelMapper modelMapper;

    @Autowired
    public VehicleMVCController(VehicleService vehicleService, UserService userService, CarModelService carModelService, CarModelRepository carModelRepository, BrandService brandService, AuthenticationHelper authenticationHelper, ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.carModelService = carModelService;
        this.carModelRepository = carModelRepository;
        this.brandService = brandService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String getAllVehicles(Model model) {
        List<Vehicle> vehicleList = vehicleService.getAll();
        model.addAttribute("vehicles", vehicleList);
        VehicleFilterDto vehicleFilterDto = new VehicleFilterDto();
        model.addAttribute("vehicleFilterDto", vehicleFilterDto);
        return "vehicles";
    }

    @PostMapping()
    public String filterVehicles(@ModelAttribute("vehicleFilterDto") VehicleFilterDto vehicleFilterDto, Model model) {
        List<Vehicle> vehicleList = vehicleService.getAllVehicles(vehicleFilterDto);
        model.addAttribute("vehicles", vehicleList);
        model.addAttribute("vehicleFilterDto", vehicleFilterDto);
        return "vehicles";
    }

    @GetMapping("/new")
    public String createNewVehicle(Model model) {
        List<Brand> brands = brandService.getAll();
        List<CarModel> carModels = carModelService.getAll();
        List<UserViewModel> users = userService.getAll();
        model.addAttribute("vehicleDto", new VehicleDto());
        model.addAttribute("brands", brands);
        model.addAttribute("carModels", carModels);
        model.addAttribute("users", users);
        return "vehicle-new";
    }

    @PostMapping("/new")
    public String createNewVehicle(@Valid @ModelAttribute("vehicleDto") VehicleDto vehicleDto) {
        User user = userService.getById(vehicleDto.getUserId().getId());
        Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);
        vehicle.setUser(user);
        vehicle.setCarModelId(vehicleDto.getCarModelId());
        vehicleService.save(vehicle);
        return "redirect:/vehicles";
    }


    @GetMapping("/vehicle-update/{vehicleId}")
    public String updateVehicle(@PathVariable("vehicleId") Long vehicleId, Model model) {
        List<CarModel> carModels = carModelService.getAll();
        List<UserViewModel> users = userService.getAll();
        model.addAttribute("carModels", carModels);
        model.addAttribute("users", users);
        Vehicle vehicle = vehicleService.getById(vehicleId);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("vehicleId", vehicleId);
        return "vehicle-update";
    }

    @PostMapping("/vehicle-update/{vehicleId}")
    public String updateVehicle(@PathVariable("vehicleId") Long vehicleId, @Valid @ModelAttribute("vehicleDto") VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleService.getById(vehicleId);
        vehicle.setVIN(vehicleDto.getVIN());
        vehicle.setCreationYear(vehicleDto.getCreationYear());
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        vehicle.setUser(vehicleDto.getUserId());
        vehicle.setCarModelId(vehicleDto.getCarModelId());
        vehicleService.save(vehicle);
        return "redirect:/vehicles";
    }

    @GetMapping("/delete/{vehicleId}")
    public String deleteVehicle(@PathVariable("vehicleId") Long vehicleId) {
        vehicleService.deleteVehicleByVehicleId(vehicleId);
        return "redirect:/vehicles";
    }
}