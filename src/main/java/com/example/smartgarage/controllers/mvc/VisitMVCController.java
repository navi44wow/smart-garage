package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.UserDto;
import com.example.smartgarage.models.dtos.VisitDto;
import com.example.smartgarage.models.entities.*;
import com.example.smartgarage.models.enums.StatusCode;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.contracts.CarServizService;
import com.example.smartgarage.services.contracts.UserService;
import com.example.smartgarage.services.contracts.VehicleService;
import com.example.smartgarage.services.contracts.VisitService;
import com.example.smartgarage.services.mappers.VisitMapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/visits")
public class VisitMVCController {

    private final VisitService visitService;

    private final UserService userService;

    private final VehicleService vehicleService;

    private final CarServizService carServizService;
    private final AuthenticationHelper authenticationHelper;
    private final ModelMapper modelMapper;
    private final VisitMapper visitMapper;

    public VisitMVCController(VisitService visitService, UserService userService, VehicleService vehicleService,
                              CarServizService carServizService,
                              AuthenticationHelper authenticationHelper,
                              ModelMapper modelMapper, VisitMapper visitMapper) {
        this.visitService = visitService;
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.carServizService = carServizService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
        this.visitMapper = visitMapper;
    }

    @GetMapping()
    public String getAll(Model model) {
        List<Visit> visits = visitService.getAll();
        model.addAttribute("all", visits);
        return "visits";
    }

    @GetMapping("/visit-view/{id}")
    public String getVisitById(@PathVariable Long id, Model model) {
        Optional<Visit> visit = visitService.getById(id);
        if (visit.isEmpty()) {
            throw new EntityNotFoundException("Visit", visit.get().getId());
        }
        model.addAttribute("visit", visit);
        return "visit-view";
    }

    @GetMapping("/visit-new")
    public String create(Model model) {
        //TODO gets crushed if checkAuthorization(headers)
        //authenticationHelper.checkAuthorization(headers);
        List<Vehicle> vehicles = vehicleService.getAll();
        List<CarService> services = carServizService.getAll();
        List<VisitStatus> statusList = visitService.findAllStatuses();
        model.addAttribute("statusList", statusList);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("services", services);
        model.addAttribute("visitDTO", new VisitDto());
        return "visit-new";
    }

    @PostMapping("/visit-new")
    public String createVisit(@ModelAttribute("visitDTO") VisitDto visitDto, Model model) {
        Visit visit = visitMapper.toObject(visitDto);
        visitService.save(visit);
        model.addAttribute("visit", visit);
        return "redirect:/visits/visit-view/"+visit.getId();
    }
}