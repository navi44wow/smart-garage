package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.VisitDto;
import com.example.smartgarage.models.entities.*;
import com.example.smartgarage.services.contracts.*;
import com.example.smartgarage.services.mappers.VisitMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String getAllVisits(Model model, @RequestParam(required = false) Boolean showArchived) {
        List<Visit> allVisits = visitService.getAll();
        if (showArchived != null && showArchived) {
            allVisits = allVisits.stream().filter(Visit::isArchived).collect(Collectors.toList());
        }
        model.addAttribute("all", allVisits);
        return "visits";
    }

    @PostMapping()
    public String archiveVisit(@RequestParam("visitId") Long visitId) {
        Visit visit = visitService.getVisitById(visitId);
        if (visit.isArchived()) {
            visit.setArchived(false);
        } else {
            visit.setArchived(true);
        }
        visitService.save(visit);
        return "redirect:/visits";
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
        //Set<Long> serviceIdSet = Arrays.stream(serviceIds).collect(Collectors.toSet());
        //visitDto.setServiceIds(serviceIdSet);
        Visit visit = visitMapper.toObject(visitDto);
        visitService.save(visit);
        model.addAttribute("visit", visit);
        return "redirect:/visits/visit-view/" + visit.getId();
    }

    @GetMapping("/visit-view/{id}")
    public String addServices(@PathVariable("id") Long id, Model model) {
        Optional<Visit> visit = visitService.getById(id);
        List<CarService> services = carServizService.getAll();
        List<VisitStatus> statusList = visitService.findAllStatuses();
        model.addAttribute("statusList", statusList);
        model.addAttribute("services", services);
        model.addAttribute("visit", visit);
        System.out.println("Services size = "+services.size());
        return "visit-view";
    }

    @PostMapping("visit-view/{id}")
    public String addServices(@PathVariable("id") Long id, @ModelAttribute("visitDTO") VisitDto visitDto,
                              @RequestParam("serviceIds") Long[] serviceIds, Model model) {
        Visit visit = visitService.getVisitById(id);
        Set<Long> serviceIdSet = Arrays.stream(serviceIds).collect(Collectors.toSet());
        visitMapper.addServices(id, visitDto, serviceIdSet);
        visitService.save(visit);
        model.addAttribute("visit", visit);
        return "redirect:/visits/visit-view/"+visit.getId();
    }
}