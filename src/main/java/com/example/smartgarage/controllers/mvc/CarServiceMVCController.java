package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.CarServiceDto;
import com.example.smartgarage.models.dtos.CarServizFilterDto;
import com.example.smartgarage.models.entities.CarService;
import com.example.smartgarage.services.contracts.CarServizService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/services")
public class CarServiceMVCController {

    private final CarServizService carServizService;
    private final AuthenticationHelper authenticationHelper;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceMVCController(CarServizService carServizService,
                                   AuthenticationHelper authenticationHelper,
                                   ModelMapper modelMapper) {
        this.carServizService = carServizService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String getAll(Model model) {
            List<CarService> servicezList = carServizService.getAllGeneric(
                    Optional.empty(),
                    Optional.of("id"),
                    Optional.empty());

            model.addAttribute("services", servicezList);
            CarServizFilterDto filterDTO = new CarServizFilterDto();
            model.addAttribute("filterDTO", filterDTO);

            return "services";
        }

    @PostMapping()
    public String filterServices(@ModelAttribute("filterDTO") CarServizFilterDto filterDTO, Model model) {
        List<CarService> servicezList = carServizService.getAllGeneric(
                Optional.ofNullable(filterDTO.getName()),
                Optional.ofNullable(filterDTO.getSortBy()),
                Optional.ofNullable(filterDTO.getSortOrder())
        );
        model.addAttribute("services", servicezList);
        model.addAttribute("filterDTO", filterDTO);

        return "services";
    }


    @GetMapping("/service-new")
    public String create(Model model) {
        model.addAttribute("carServiceDTO", new CarServiceDto());
        return "/service-new";
    }

    @PostMapping("/service-new")
    public String create(@Valid @ModelAttribute("serviceDto") CarServiceDto serviceDto, BindingResult result,
                         @RequestHeader("Authorization") HttpHeaders headers, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "service-new";
        }

        try {
            CarService service = modelMapper.map(serviceDto, CarService.class);
            carServizService.create(service);
            redirectAttributes.addAttribute("id", service.getId());
            return "redirect:/services";
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please choose a different name for the service.");
            return "redirect:/services/service-new";
        }
    }

    @GetMapping("/service-update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        CarService service = carServizService.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid service Id:" + id));
        model.addAttribute("service", service);
        model.addAttribute("id", id);
        return "service-update";
    }

    @PostMapping("/service-update/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("serviceDto") CarServiceDto serviceDto,
                         BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "service-update";
        }
        try {
            CarService service = carServizService.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Invalid service Id:" + id));
            modelMapper.map(serviceDto, service);
            carServizService.update(service, serviceDto);
            redirectAttributes.addAttribute("id", id);
            return "redirect:/services";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please choose a different name for the service.");
            return "redirect:/services/service-update/"+id;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            carServizService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Service successfully deleted.");
            return "redirect:/services";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}