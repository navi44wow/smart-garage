package com.example.smartgarage.controllers.rest;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.ModelDto;
import com.example.smartgarage.models.entities.CarModel;
import com.example.smartgarage.services.contracts.CarModelService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/models")
public class CarModelRestController {

    private final CarModelService carModelService;
    private final ModelMapper modelMapper;
    private final AuthenticationHelper authenticationHelper;

    public CarModelRestController(CarModelService carModelService, ModelMapper modelMapper
            , AuthenticationHelper authenticationHelper
    ) {
        this.carModelService = carModelService;
        this.modelMapper = modelMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping()
    public List<CarModel> getAll(@RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return carModelService.getAll();
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public CarModel getById(@RequestHeader("Authorization") HttpHeaders headers, @PathVariable Long id) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return carModelService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @PostMapping("/new")
    public CarModel createModel(
            @RequestHeader("Authorization") HttpHeaders headers,
            @Valid @RequestBody ModelDto modelDto) {
        CarModel carModel;
        try {
            authenticationHelper.checkAuthorization(headers);
            carModel = modelMapper.map(modelDto, CarModel.class);
            carModelService.save(carModel);
            return carModelService.getById(carModel.getModelId());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{modelId}/update")
    public CarModel updateModel(@PathVariable Long modelId,
                                @Valid @RequestBody ModelDto modelDto,
                                @RequestHeader("Authorization") HttpHeaders headers) {
        CarModel existingCarModel;
        try {
            authenticationHelper.checkAuthorization(headers);
            existingCarModel = carModelService.getById(modelId);
            return carModelService.update(existingCarModel, modelDto);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{modelId}")
    public void deleteModel(@PathVariable Long carModelId,
                            @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            carModelService.deleteCarModelById(carModelId);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/name/{modelName}")
    public CarModel getByModelName(@RequestHeader("Authorization") HttpHeaders headers, @PathVariable String modelName) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return carModelService.getByName(modelName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/brandName/{brandName}")
    public List<CarModel> getAllByBrandName(@RequestHeader("Authorization") HttpHeaders headers, @PathVariable String brandName) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return carModelService.getByBrandName(brandName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/brandId/{brandId}")
    public List<CarModel> getAllByBrandId(@RequestHeader("Authorization") HttpHeaders headers, @PathVariable Long brandId) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return carModelService.getByBrandId(brandId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}