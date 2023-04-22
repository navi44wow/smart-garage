package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.VisitDto;
import com.example.smartgarage.models.entities.*;
import com.example.smartgarage.services.contracts.*;
import com.example.smartgarage.services.mappers.VisitMapper;
import org.modelmapper.ModelMapper;
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

    private final UserService userService;

    private final VehicleService vehicleService;

    private final CarServizService carServizService;
    private final AuthenticationHelper authenticationHelper;
    private final ModelMapper modelMapper;
    private final VisitMapper visitMapper;
    private final PDFGeneratorService pdfGeneratorService;

    public VisitMVCController(VisitService visitService, UserService userService, VehicleService vehicleService,
                              CarServizService carServizService,
                              AuthenticationHelper authenticationHelper,
                              ModelMapper modelMapper, VisitMapper visitMapper, PDFGeneratorService pdfGeneratorService) {
        this.visitService = visitService;
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.carServizService = carServizService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
        this.visitMapper = visitMapper;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @GetMapping()
    public String getAllVisits(Model model, @RequestParam(required = false) Boolean showArchived) {
        List<Visit> allVisits = visitService.getAll();
        if (showArchived != null && showArchived) {
            allVisits = allVisits.stream().filter(Visit::isArchived).collect(Collectors.toList());
        } else {
            allVisits = allVisits.stream()
                    .filter(visit -> !visit.isArchived())
                    .sorted(Comparator.comparing(Visit::getId).reversed())
                    .collect(Collectors.toList());
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
        Visit visit = visitMapper.toObject(visitDto);
        visitService.save(visit);
        model.addAttribute("visit", visit);
        return "redirect:/visits/visit-view/" + visit.getId();
    }

    @GetMapping("/visit-view/{id}")
    public String addServices(@PathVariable("id") Long id, Model model) {
        Optional<Visit> visit = visitService.getById(id);
        Set<ListOfServices> listOfServices = visit.get().getServices();
        List<CarService> services = carServizService.getAll();
        List<CarService> filteredServices = new ArrayList<>();
        for (CarService service : services) {
            boolean isPresent = false;
            for (ListOfServices listOfService : listOfServices) {
                if (listOfService.getServiceID().equals(service.getId())) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                filteredServices.add(service);
            }
        }

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
        if(optionalVisit.isPresent()) {
            Visit visit = optionalVisit.get();
            this.pdfGeneratorService.export(response, visit);
        }
    }

}