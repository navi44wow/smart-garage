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
import com.example.smartgarage.services.VehicleMapper;
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
import java.util.Optional;

@Controller
@RequestMapping("/vehicles")
public class VehicleMVCController {

    private final VehicleService vehicleService;
    private final UserService userService;

    private final CarModelService carModelService;

    private final CarModelRepository carModelRepository;

    private final BrandService brandService;

    private final AuthenticationHelper authenticationHelper;
    private final VehicleMapper vehicleMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleMVCController(VehicleService vehicleService, UserService userService, CarModelService carModelService, CarModelRepository carModelRepository, BrandService brandService, AuthenticationHelper authenticationHelper, VehicleMapper vehicleMapper, ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.carModelService = carModelService;
        this.carModelRepository = carModelRepository;
        this.brandService = brandService;
        this.authenticationHelper = authenticationHelper;
        this.vehicleMapper = vehicleMapper;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String getAllVehicles(Model model) {
        List<Vehicle> vehicleList = vehicleService.getAllGenericVehicles(
                Optional.empty(),
                Optional.of("creationYear"),
                Optional.empty());
        model.addAttribute("vehicles", vehicleList);
        VehicleFilterDto vehicleFilterDto = new VehicleFilterDto();
        model.addAttribute("vehicleFilterDto", vehicleFilterDto);
        return "vehicles";
    }

    @PostMapping()
    public String filterVehicles(@ModelAttribute("vehicleFilterDto") VehicleFilterDto vehicleFilterDto, Model model) {
        List<Vehicle> vehicleList = vehicleService.getAllGenericVehicles(
                Optional.ofNullable(vehicleFilterDto.getCreationYear()),
                Optional.ofNullable(vehicleFilterDto.getSortBy()),
                Optional.ofNullable(vehicleFilterDto.getSortOrder())
        );
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
        User user = userService.getById(vehicleDto.getUserId());
        CarModel carModel = carModelService.getById(vehicleDto.getCarModelId());
        Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);
        vehicleService.save(vehicle);
        vehicle.setUser(user);
        vehicle.setCarModelId(carModel);
        return "redirect:/vehicles";
    }

    @GetMapping("/delete/{vehicleId}")
    public String deleteVehicle(@PathVariable("vehicleId") Long vehicleId) {

        vehicleService.deleteVehicleByVehicleId(vehicleId);
        return "redirect:/vehicles";
    }

    //Todo
    //update
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
        Long vehId = vehicle.getVehicleId();
        CarModel carModel = carModelService.getById(vehicleDto.getCarModelId());
        vehicle.setCarModelId(carModel);
        modelMapper.map(vehicleDto, vehicle);
        vehicle.setVehicleId(vehId);
        vehicleService.save(vehicle);
        return "redirect:/vehicles";

    }
}