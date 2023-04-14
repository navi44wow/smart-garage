package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.CarServiceDto;
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

@Controller
@RequestMapping("/services")
public class CarServiceMVCController {

    private final CarServizService carServizService;
    private final AuthenticationHelper authenticationHelper;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceMVCController(CarServizService carServizService, AuthenticationHelper authenticationHelper, ModelMapper modelMapper) {
        this.carServizService = carServizService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String getAll(Model model) {
        List<CarService> services = carServizService.getAll();
        model.addAttribute("all", services);
        return "services";
    }

    @GetMapping("/service-new")
    public String create(Model model) {
        //TODO gets crushed if checkAuthorization(headers)
        //authenticationHelper.checkAuthorization(headers);
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
            //TODO gets crushed if checkAuthorization(headers)
            //authenticationHelper.checkAuthorization(headers);
            CarService service = modelMapper.map(serviceDto, CarService.class);
            carServizService.save(service);
            redirectAttributes.addAttribute("id", service.getId());
            return "redirect:/services";
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/service-update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        CarService service = carServizService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + id));
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
            CarService service = carServizService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + id));
            modelMapper.map(serviceDto, service);
            carServizService.save(service);
            redirectAttributes.addAttribute("id", id);
            return "redirect:/services";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
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
