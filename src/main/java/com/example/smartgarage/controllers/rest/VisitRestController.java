package com.example.smartgarage.controllers.rest;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.entities.Visit;
import com.example.smartgarage.services.contracts.UserService;
import com.example.smartgarage.services.contracts.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/visits")
public class VisitRestController {

    private final VisitService visitService;

    private final ModelMapper modelMapper;

    private final AuthenticationHelper authenticationHelper;

    public VisitRestController(VisitService visitService, ModelMapper modelMapper, AuthenticationHelper authenticationHelper) {
        this.visitService = visitService;
        this.modelMapper = modelMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Visit> getAll() {
        return visitService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Visit> getVisitById(@PathVariable Long id) {
        try {
            return visitService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/username/{username}")
    public List<Visit> getAllByUsername(@PathVariable String username) {
        return visitService.getAllByUsername(username);
    }
}
