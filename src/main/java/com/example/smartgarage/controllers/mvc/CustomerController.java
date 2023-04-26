package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.exceptions.NotValidPasswordException;
import com.example.smartgarage.exceptions.PasswordConfirmationException;
import com.example.smartgarage.models.dtos.GenerateUserDto;
import com.example.smartgarage.models.dtos.UserDto;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.UserRoleEntity;
import com.example.smartgarage.models.dtos.VisitFilterDto;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.models.entities.Visit;
import com.example.smartgarage.models.enums.UserRole;
import com.example.smartgarage.models.service_models.UserServiceModel;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.repositories.UserRepository;
import com.example.smartgarage.repositories.VehicleRepository;
import com.example.smartgarage.services.contracts.PDFGeneratorService;
import com.example.smartgarage.services.contracts.UserRoleService;
import com.example.smartgarage.services.contracts.UserService;
import com.example.smartgarage.services.contracts.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CustomerController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    private final VisitService visitService;

    private final PDFGeneratorService pdfGeneratorService;

    private final UserRoleService userRoleService;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public CustomerController(UserService userService, ModelMapper modelMapper, VisitService visitService, PDFGeneratorService pdfGeneratorService,
                              UserRoleService userRoleService, UserRepository userRepository,
                              VehicleRepository vehicleRepository) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.visitService = visitService;
        this.pdfGeneratorService = pdfGeneratorService;
        this.userRoleService = userRoleService;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @ModelAttribute("loggedInUser")
    public User loggedInUser() {
        return getLoggedInUser();
    }

    @ModelAttribute("generateUserDto")
    public GenerateUserDto createGenerateUserModel() {
        return new GenerateUserDto();
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/customer")
    public String home() {
        return "customer-dashboard";
    }

    @GetMapping("/detailsPage/{username}")
    public String detailsPage(@PathVariable String username, Model model){
        model.addAttribute("forbidden", false);
        UserRoleEntity userRole = userRoleService.getByUserRole(UserRole.EMPLOYEE);
        UserViewModel userViewModel = userService.getByUsername(username);
        model.addAttribute("isEmployee", false);
        if (loggedInUser().getRoles().contains(userRole)){
            model.addAttribute("isEmployee", true);
        }
        if (!loggedInUser().getUsername().equals(username) && userViewModel.getRoles().contains(userRole)){
            model.addAttribute("forbidden", true);
        }
        if (!loggedInUser().getUsername().equals(username) && !loggedInUser().getRoles().contains(userRole)){
            return "error";
        }
        model.addAttribute("cars", vehicleRepository.findAllByUserId(userViewModel.getId()));
        model.addAttribute("user", userService.getByUsername(username));
        model.addAttribute("username", username);
        return "user-details-page";
    }


    @GetMapping("/userInfoUpdate/{username}")
    public String updateUserInfo(@PathVariable String username, Model model){
        model.addAttribute("forbidden", false);
        if (!loggedInUser().getUsername().equals(username)){
            model.addAttribute("forbidden", true);
        }
        UserRoleEntity userRole = userRoleService.getByUserRole(UserRole.EMPLOYEE);
        UserViewModel user = userService.getByUsername(username);
        if (!loggedInUser().getUsername().equals(username) && !loggedInUser().getRoles().contains(userRole)
                || user.getRoles().contains(userRole) && !user.getUsername().equals(loggedInUser().getUsername())){
            return "error";
        }
        model.addAttribute("user", userService.getByUsername(username));
        model.addAttribute("username", username);
        model.addAttribute("userUpdate", new UserDto());
        return "update-user-details-info-page";
    }


    private String checkAuthorization(String username) {
        UserRoleEntity userRole = userRoleService.getByUserRole(UserRole.EMPLOYEE);
        if (!loggedInUser().getUsername().equals(username) && !loggedInUser().getRoles().contains(userRole)){
            return "error";
        }
        return null;
    }

    @PostMapping("/userInfoUpdate/{username}")
    public String updateUserInfo(@PathVariable String username, @ModelAttribute("userUpdate") UserDto userDto, Model model){


        return "user-details-page";
    }

    @GetMapping("/resetPassword/{username}")
    public String getResetPassword(@PathVariable String username, Model model) {
        if (!loggedInUser().getUsername().equals(username)){
            model.addAttribute("forbidden", true);
        }
        if (!loggedInUser().getUsername().equals(username)){
            return "error";
        }
        model.addAttribute("username", username);
        return "reset_password";
    }

    @PostMapping("/resetPassword/{username}")
    public String handleForgotPassword(@PathVariable String username, GenerateUserDto generateUserDto,
                                       Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("generateUserDto", generateUserDto);
            model.addAttribute("org.springframework.validation.BindingResult.generateUserDto", bindingResult);
        }
        generateUserDto.setUsername(username);
        UserServiceModel userServiceModel = modelMapper.map(generateUserDto, UserServiceModel.class);
        try {
            userService.updatePassword(userServiceModel);
        } catch (NotValidPasswordException e) {
            model.addAttribute("generateUserDto", generateUserDto);
            model.addAttribute("invalidPassword", true);
            model.addAttribute("exceptionPassMessage", e.getMessage());
            return "reset_password";
        } catch (PasswordConfirmationException e) {
            model.addAttribute("generateUserDto", generateUserDto);
            model.addAttribute("notConfirmedPassword", true);
            model.addAttribute("confirmationExcMessage", e.getMessage());
            return "reset_password";
        } catch (EntityNotFoundException e) {
            model.addAttribute("generateUserDto", generateUserDto);
            model.addAttribute("username", username);
            model.addAttribute("userNotFound", true);
            return "reset_password";
        }

        return "redirect:/users/login";
    }

    @GetMapping("/customer/{username}/customer-visits")
    public String getAll(@PathVariable("username") String username, Model model,
                         @ModelAttribute("filterOptions") VisitFilterDto visitFilterDto) {
        UserViewModel user = userService.getByUsername(username);
        List<Visit> allVisits = visitService.getAll().stream()
                .filter(visit -> visit.getVehicle().getUser().getUsername().equals(username))
                .collect(Collectors.toList());

        if (visitFilterDto.getBrand() != null && !visitFilterDto.getBrand().isEmpty()) {
            allVisits = filterVisitsByBrand(allVisits, visitFilterDto.getBrand());
        }
        if (visitFilterDto.getStatus() != null && !visitFilterDto.getStatus().isEmpty()) {
            allVisits = filterVisitsByStatus(allVisits, visitFilterDto.getStatus());
        }
        if (visitFilterDto.getStartDate() != null && visitFilterDto.getEndDate() != null) {
            LocalDate startDate = visitFilterDto.getStartDate();
            LocalDate endDate = visitFilterDto.getEndDate();
            allVisits = filterVisitsByDateRange(allVisits, startDate, endDate);
        } else if (visitFilterDto.getStartDate() != null) {
            LocalDate startDate = visitFilterDto.getStartDate();
            allVisits = filterVisitsByStartDate(allVisits, startDate);
        }

        List<Vehicle> vehicles = allVisits.stream()
                .map(Visit::getVehicle)
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("visits", allVisits);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("username", user.getUsername());
        return "customer-visits";
    }

    @GetMapping("/customer/{username}/customer-visits/customer-visit-view/{visitId}")
    public String getVisit(@PathVariable("visitId") Long visitId,
                           @PathVariable("username") String username,
                           Model model) {
        Optional<Visit> visit = visitService.getById(visitId);
        model.addAttribute("visit", visit);
        model.addAttribute("username", visit.get().getVehicle().getUser().getUsername());
        return "customer-visit-view";
    }

    @GetMapping("/customer/visit-invoice/{id}/pdf/generate")
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


    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return modelMapper.map(userService.getByUsername(authentication.getName()), User.class);
    }

    private List<Visit> filterVisitsByBrand(List<Visit> visits, String brand) {
        return visits.stream()
                .filter(visit -> visit.getVehicle().getCarModelId().getBrand().getBrandName().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    private List<Visit> filterVisitsByStatus(List<Visit> visits, String status) {
        return visits.stream()
                .filter(visit -> visit.getStatus().getName().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    private List<Visit> filterVisitsByDateRange(List<Visit> visits, LocalDate startDate, LocalDate endDate) {
        return visits.stream().filter(visit -> visit.getStartDate().isAfter(startDate.minusDays(1)) &&
                visit.getStartDate().isBefore(endDate.plusDays(1))).collect(Collectors.toList());
    }

    private List<Visit> filterVisitsByStartDate(List<Visit> visits, LocalDate startDate) {
        return visits.stream().filter(visit -> visit.getStartDate().isEqual(startDate)).collect(Collectors.toList());
    }

}