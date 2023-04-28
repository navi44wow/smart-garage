package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.models.dtos.VisitDto;
import com.example.smartgarage.models.dtos.VisitFilterDto;
import com.example.smartgarage.models.entities.*;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.contracts.*;
import com.example.smartgarage.services.mappers.VisitMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/visits")
public class VisitMVCController {
    private final VisitService visitService;
    private final VehicleService vehicleService;
    private final BrandService brandService;
    private final CarServizService carServizService;
    private final UserService userService;
    private final VisitMapper visitMapper;
    private final PDFGeneratorService pdfGeneratorService;

    public VisitMVCController(VisitService visitService,
                              VehicleService vehicleService,
                              BrandService brandService, CarServizService carServizService,
                              UserService userService, VisitMapper visitMapper,
                              PDFGeneratorService pdfGeneratorService) {
        this.visitService = visitService;
        this.vehicleService = vehicleService;
        this.brandService = brandService;
        this.carServizService = carServizService;
        this.userService = userService;
        this.visitMapper = visitMapper;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @GetMapping()
    public String getAllVisits(Model model,
                               @RequestParam(required = false) Boolean onlyArchived,
                               @RequestParam(required = false) Boolean includeArchived,
                               @ModelAttribute("filterOptions") VisitFilterDto visitFilterDto) {
        List<Visit> allVisits = visitService.getAllVisits(onlyArchived, includeArchived, visitFilterDto);
        List<Brand> brands = brandService.getAll();
        List<UserViewModel> users = userService.getAll();
        model.addAttribute("all", allVisits);
        model.addAttribute("brands", brands);
        model.addAttribute("users", users);
        model.addAttribute("filterOptions", visitFilterDto);

        return "visits";
    }

    @PostMapping()
    public String archiveVisit(@RequestParam("visitId") Long visitId) {
        Visit visit = visitService.getVisitById(visitId);
        visit = visitService.toggleArchivedStatus(visit);
        visitService.save(visit);
        return "redirect:/visits";
    }


    @GetMapping("/visit-new")
    public String create(Model model) {
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
        return "redirect:/visits/visit-view/" + visit.getId();
    }

    @GetMapping("/visit-view/{id}")
    public String addServices(@PathVariable("id") Long id, Model model) {
        Optional<Visit> visit = visitService.getById(id);
        List<CarService> filteredServices = visitService.getFilteredCarServices(visit.get());
        List<VisitStatus> statusList = visitService.findAllStatuses();
        model.addAttribute("statusList", statusList);
        model.addAttribute("services", filteredServices);
        model.addAttribute("visit", visit);
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
        return "redirect:/visits/visit-view/" + visit.getId();
    }

    @GetMapping("visit-update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        Optional<Visit> visit = visitService.getById(id);
        List<VisitStatus> statusList = visitService.findAllStatuses();
        model.addAttribute("statusList", statusList);
        model.addAttribute("visit", visit);
        return "visit-update";
    }

    @PostMapping("/visit-update/{id}")
    public String updateVisit(@PathVariable("id") Long id, @ModelAttribute("visit") Visit visit,
                              @RequestParam("newStatus") String newStatus) {
        Visit existingVisit = visitService.getVisitById(id);
        existingVisit.setStatus(VisitStatus.valueOf(newStatus));
        existingVisit.setDueDate(visit.getDueDate());
        visitService.save(existingVisit);
        return "redirect:/visits/visit-view/" + existingVisit.getId();
    }

    @GetMapping("visit-update/{visitId}/remove-service/{serviceId}")
    public String removeService(@PathVariable("visitId") Long visitId,
                                @PathVariable("serviceId") Long serviceId) {
        Optional<Visit> visitOptional = visitService.getById(visitId);
        if (visitOptional.isEmpty()) {
            return "redirect:/error";
        }
        Visit visit = visitOptional.get();
        Optional<ListOfServices> serviceOptional = visit.getServices().stream()
                .filter(s -> s.getId().equals(serviceId))
                .findFirst();
        if (!serviceOptional.isPresent()) {
            return "redirect:/error";
        }
        ListOfServices service = serviceOptional.get();
        visit.getServices().remove(service);
        visitService.save(visit);
        return "redirect:/visits/visit-update/" + visitId;
    }

    @GetMapping("visit-invoice/{id}")
    public String facture(@PathVariable("id") Long id, Model model) {
        Optional<Visit> visit = visitService.getById(id);
        List<VisitStatus> statusList = visitService.findAllStatuses();
        model.addAttribute("statusList", statusList);
        model.addAttribute("visit", visit);
        return "visit-invoice";
    }

    @GetMapping("/visit-invoice/{id}/pdf/generate")
    public void generatePDF(HttpServletResponse response, @PathVariable("id") Long visitId) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd:hh:mm");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=visit_" + visitId + "_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Optional<Visit> optionalVisit = visitService.getById(visitId);
        if (optionalVisit.isPresent()) {
            Visit visit = optionalVisit.get();
            this.pdfGeneratorService.export(response, visit);
        }
    }
}