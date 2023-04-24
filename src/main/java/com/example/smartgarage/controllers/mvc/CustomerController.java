package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.exceptions.NotValidPasswordException;
import com.example.smartgarage.exceptions.PasswordConfirmationException;
import com.example.smartgarage.models.dtos.GenerateUserDto;
import com.example.smartgarage.models.entities.Visit;
import com.example.smartgarage.models.service_models.UserServiceModel;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.contracts.PDFGeneratorService;
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

    public CustomerController(UserService userService, ModelMapper modelMapper, VisitService visitService, PDFGeneratorService pdfGeneratorService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.visitService = visitService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @ModelAttribute("loggedInUser")
    public UserViewModel loggedInUser() {
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

    @GetMapping("/resetPassword/{username}")
    public String getResetPassword(@PathVariable String username, Model model) {
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
    public String getAll(@PathVariable("username") String username, Model model) {
        UserViewModel user = userService.getByUsername(username);
        List<Visit> visits = visitService.getAll().stream()
                .filter(visit -> visit.getVehicle().getUser().getUsername().equals(user.getUsername()))
                .collect(Collectors.toList());
        model.addAttribute("visits", visits);
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


    private UserViewModel getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return userService.getByUsername(authentication.getName());
    }

}
