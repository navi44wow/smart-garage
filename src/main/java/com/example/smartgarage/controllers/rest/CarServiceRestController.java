package com.example.smartgarage.controllers.rest;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.exceptions.InvalidRequestException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.CarServiceDto;
import com.example.smartgarage.models.entities.CarService;
import com.example.smartgarage.repositories.CarServiceRepository;
import com.example.smartgarage.services.contracts.CarServizService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
public class CarServiceRestController {

    private final CarServizService carService;

    private final ModelMapper modelMapper;

    private final AuthenticationHelper authenticationHelper;


    public CarServiceRestController(CarServizService carService, ModelMapper modelMapper, AuthenticationHelper authenticationHelper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
        this.authenticationHelper = authenticationHelper;
    }


    @GetMapping
    public List<CarService> getAll() {
        return carService.getAll();
    }

    @GetMapping("/filter")
    public List<CarService> getAll(@RequestParam(required = false) Optional<String> name,
                                 @RequestParam(required = false) Optional<Integer> priceMinimum,
                                 @RequestParam(required = false) Optional<Integer> priceMaximum,
                                 @RequestParam(required = false) Optional<String> sortBy,
                                 @RequestParam(required = false) Optional<String> sortOrder) {
        try {
            return carService.getAll(name, priceMinimum, priceMaximum, sortBy, sortOrder);
        } catch (InvalidRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }



    @GetMapping("/{id}")
    public Optional<CarService> getServiceById(@PathVariable Long id) {
        try {
            return carService.findById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/new")
    public Optional<CarService> create(@Valid @RequestBody CarServiceDto serviceDto,
                                       @RequestHeader("Authorization") HttpHeaders headers) {
        CarService service;
        try {
            authenticationHelper.checkAuthorization(headers);
            service = modelMapper.map(serviceDto, CarService.class);
            carService.save(service);
            return carService.findById(service.getId());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/update")
    public CarService update(@PathVariable Long id,
                                       @Valid @RequestBody CarServiceDto serviceDto,
                                       @RequestHeader("Authorization") HttpHeaders headers) {
        CarService existingCarService;
        try {
            authenticationHelper.checkAuthorization(headers);
            existingCarService = carService.getById(id);
            return carService.update(existingCarService, serviceDto);
            } catch (AuthorizationException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            carService.delete(id);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
