package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.models.dtos.ModelDto;
import com.example.smartgarage.models.dtos.ModelFilterDto;

import com.example.smartgarage.models.entities.CarModel;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/models")
public class BrandModelMVCController {

    private final VehicleService vehicleService;
    private final BrandService brandService;
    private final CarModelService carModelService;
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final VehicleMapper vehicleMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public BrandModelMVCController(VehicleService vehicleService, BrandService brandService, CarModelService carModelService, UserService userService, AuthenticationHelper authenticationHelper, VehicleMapper vehicleMapper, ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.brandService = brandService;
        this.carModelService = carModelService;
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.vehicleMapper = vehicleMapper;
        this.modelMapper = modelMapper;
    }


    @GetMapping()
    public String getAllBrandsModels(Model model) {
        List<CarModel> carModelList = carModelService.getAll();
        model.addAttribute("models", carModelList);

        ModelFilterDto modelFilterDto = new ModelFilterDto();
        model.addAttribute("modelFilterDto", modelFilterDto);

        return "models";
    }


    @GetMapping("/new")
    public String createNewModel(Model model, HttpSession httpSession) {
        //   User user;
//        try {
//            user = authenticationHelper.tryGetUser(httpSession);
//        } catch (AuthorizationException e) {
//            return "redirect:/auth/login";
//        }
        model.addAttribute("modelDto", new ModelDto());
        //model.addAttribute("user", user.);
        return "model-new";
    }

    @PostMapping("/new")
    public String createNewModel(@Valid @ModelAttribute("modelDto") ModelDto modelDto, BindingResult bindingResult, HttpSession httpSession) {
//        User user;
//        try {
//            user = authenticationHelper.tryGetUser(httpSession);
//        } catch (AuthorizationException e) {
//            return "redirect:/auth/login";
//        }
//        if (bindingResult.hasErrors()) {
//            return "comment_new";
//        }
//        try {
        CarModel carModel = modelMapper.map(modelDto, CarModel.class);
        carModelService.save(carModel);
        return "/model-new";
    }
}
