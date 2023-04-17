package com.example.smartgarage.controllers.rest;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.ModelDto;
import com.example.smartgarage.models.entities.Model;
import com.example.smartgarage.services.contracts.ModelService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/models")
public class ModelRestController {

    private final ModelService modelService;
    private final ModelMapper modelMapper;
    private final AuthenticationHelper authenticationHelper;

    public ModelRestController(ModelService modelService, ModelMapper modelMapper
            , AuthenticationHelper authenticationHelper
    ) {
        this.modelService = modelService;
        this.modelMapper = modelMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping()
    public List<Model> getAll(@RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return modelService.getAll();
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Model getById(@RequestHeader("Authorization") HttpHeaders headers, @PathVariable Long id) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return modelService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/name/{modelName}")
    public Model getByModelName(@RequestHeader("Authorization") HttpHeaders headers, @PathVariable String modelName) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return modelService.getByName(modelName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/brandName/{brandName}")
    public List<Model> getAllByBrandName(@RequestHeader("Authorization") HttpHeaders headers, @PathVariable String brandName) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return modelService.getByBrandName(brandName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/brandId/{brandId}")
    public List<Model> getAllByBrandId(@RequestHeader("Authorization") HttpHeaders headers, @PathVariable Long brandId) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return modelService.getByBrandId(brandId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/new")
    public Model createModel(
            @RequestHeader("Authorization") HttpHeaders headers,
            @Valid @RequestBody ModelDto modelDto) {
        Model model;
        try {
            authenticationHelper.checkAuthorization(headers);
            model = modelMapper.map(modelDto, Model.class);
            modelService.save(model);
            return modelService.getById(model.getId());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{modelId}/update")
    public Model updateModel(@PathVariable Long modelId,
                             @Valid @RequestBody ModelDto modelDto,
                             @RequestHeader("Authorization") HttpHeaders headers) {
        Model existingModel;
        try {
            authenticationHelper.checkAuthorization(headers);
            existingModel = modelService.getById(modelId);
            return modelService.update(existingModel, modelDto);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{modelId}")
    public void deleteModel(@PathVariable Long modelId,
                            @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            modelService.deleteModelById(modelId);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}