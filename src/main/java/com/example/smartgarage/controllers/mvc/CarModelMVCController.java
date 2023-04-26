package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.dtos.*;

import com.example.smartgarage.models.entities.*;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.services.VehicleMapper;
import com.example.smartgarage.services.contracts.BrandService;
import com.example.smartgarage.services.contracts.CarModelService;
import com.example.smartgarage.services.contracts.UserService;
import com.example.smartgarage.services.contracts.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/carModels")
public class CarModelMVCController {

    private final VehicleService vehicleService;
    private final BrandService brandService;
    private final CarModelService carModelService;
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final VehicleMapper vehicleMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public CarModelMVCController(VehicleService vehicleService, BrandService brandService, CarModelService carModelService, UserService userService, AuthenticationHelper authenticationHelper, VehicleMapper vehicleMapper, ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.brandService = brandService;
        this.carModelService = carModelService;
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.vehicleMapper = vehicleMapper;
        this.modelMapper = modelMapper;
    }


    @GetMapping()
    public String getAllCarModels(Model model) {
        List<CarModel> carModelList = carModelService.getAll();
        model.addAttribute("models", carModelList);
        CarModelFilterDto carModelFilterDto = new CarModelFilterDto();
        model.addAttribute("carModelFilterDto", carModelFilterDto);
        return "models";
    }

    @GetMapping("/new")
    public String createNewModel(Model model) {
        List<Brand> brands = brandService.getAll();
        model.addAttribute("carModelDto", new CarModelDto());
        model.addAttribute("brands", brands);
        return "model-new";
    }


    @PostMapping("/new")
    public String createNewModel(@Valid @ModelAttribute("carModelDto") CarModelDto carModelDto) {
        CarModel carModel = modelMapper.map(carModelDto, CarModel.class);
        Brand brand = brandService.getById(carModelDto.getBrand().getId());
        carModel.setBrand(brand);
        carModelService.save(carModel);
        return "redirect:/carModels";
    }

    @GetMapping("/carModel-update/{id}")
    public String updateModel(@PathVariable("id") Long id, Model model) {
        List<Brand> brands = brandService.getAll();
        model.addAttribute("brands", brands);
        CarModel carModel = carModelService.getById(id);
        model.addAttribute("carModel", carModel);
        model.addAttribute("id", id);
        return "model-update";
    }


    @PostMapping("/carModel-update/{id}")
    public String updateModel(@PathVariable("id") Long id, @Valid @ModelAttribute("carModelDto") CarModelDto carModelDto) {
        CarModel carModel = carModelService.getById(id);
        Brand brand = brandService.getById(carModelDto.getBrand().getId());
        carModel.setBrand(brand);
        carModelService.update(carModel, carModelDto);
        return "redirect:/carModels";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            carModelService.deleteCarModelById(id);
            redirectAttributes.addFlashAttribute("message", "Car model successfully deleted.");
            return "redirect:/carModels";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}