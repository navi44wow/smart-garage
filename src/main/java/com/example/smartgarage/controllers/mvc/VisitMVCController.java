package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.entities.CarService;
import com.example.smartgarage.models.entities.Visit;
import com.example.smartgarage.services.contracts.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/visits")
public class VisitMVCController {

    private final VisitService visitService;
    private final AuthenticationHelper authenticationHelper;

    public VisitMVCController(VisitService visitService, AuthenticationHelper authenticationHelper) {
        this.visitService = visitService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping()
    public String getAll(Model model) {
        List<Visit> visits = visitService.getAll();
        model.addAttribute("all", visits);
        return "visits";
    }

}
