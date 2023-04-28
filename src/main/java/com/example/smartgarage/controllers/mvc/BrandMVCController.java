package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.BrandDto;
import com.example.smartgarage.models.dtos.BrandFilterDto;
import com.example.smartgarage.models.entities.Brand;
import com.example.smartgarage.services.contracts.BrandService;
import com.example.smartgarage.services.contracts.CarModelService;
import com.example.smartgarage.services.contracts.UserService;
import com.example.smartgarage.services.contracts.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/brands")
public class BrandMVCController {

    private final VehicleService vehicleService;
    private final BrandService brandService;
    private final CarModelService carModelService;
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final ModelMapper modelMapper;

    @Autowired
    public BrandMVCController(VehicleService vehicleService, BrandService brandService, CarModelService carModelService, UserService userService, AuthenticationHelper authenticationHelper,  ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.brandService = brandService;
        this.carModelService = carModelService;
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String getAllBrands(Model model) {
        List<Brand> brandList = brandService.getAllGeneric(
                Optional.empty(),
                Optional.of("brandName"),
                Optional.empty());
        model.addAttribute("brands", brandList);
        BrandFilterDto brandFilterDto = new BrandFilterDto();
        model.addAttribute("brandFilterDto", brandFilterDto);
        return "brands";
    }


    @PostMapping()
    public String filterBrands(@ModelAttribute("brandFilterDto") BrandFilterDto brandFilterDto, Model model) {
        List<Brand> brandList = brandService.getAllGeneric(
                Optional.ofNullable(brandFilterDto.getBrandName()),
                Optional.ofNullable(brandFilterDto.getSortBy()),
                Optional.ofNullable(brandFilterDto.getSortOrder())
        );
        model.addAttribute("brands", brandList);
        model.addAttribute("brandFilterDto", brandFilterDto);

        return "brands";
    }



    @GetMapping("/new")
    public String createNewBrand(Model model) {
        model.addAttribute("brandDto", new BrandDto());
        return "brand-new";
    }

    @PostMapping("/new")
    public String createNewBrand(@Valid @ModelAttribute("brandDto") BrandDto brandDto) {
        Brand brand = modelMapper.map(brandDto, Brand.class);
        brand.setBrandName(brandDto.getBrandName());
        brandService.save(brand);
        return "redirect:/brands";
    }

    @GetMapping("/brand-update/{id}")
    public String updateBrand(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.getById(id);
        model.addAttribute("brand", brand);
        model.addAttribute("id", id);
        return "brand-update";
    }

    @PostMapping("/brand-update/{id}")
    public String updateBrand(@PathVariable("id") Long id, @Valid @ModelAttribute("brandDto") BrandDto brandDto) {
        Brand brand = brandService.getById(id);
        brandService.update(brand, brandDto);
        return "redirect:/brands";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            brandService.deleteBrandById(id);
            redirectAttributes.addFlashAttribute("message", "Brand successfully deleted.");
            return "redirect:/brands";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}


