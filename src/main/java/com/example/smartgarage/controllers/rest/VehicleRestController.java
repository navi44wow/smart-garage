package com.example.smartgarage.controllers.rest;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.services.contracts.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/vehicles")


public class VehicleRestController {


    private final VehicleService vehicleService;

    private final ModelMapper modelMapper;

    private final AuthenticationHelper authenticationHelper;

    public VehicleRestController(VehicleService vehicleService, ModelMapper modelMapper
            , AuthenticationHelper authenticationHelper
    ) {
        this.vehicleService = vehicleService;
        this.modelMapper = modelMapper;
        this.authenticationHelper = authenticationHelper;
    }


    @GetMapping()
    public List<Vehicle> getAll() {
        return vehicleService.getAll();
    }

    @GetMapping("/search/{model}")
    public List<Vehicle> searchAllByModel(@PathVariable String model) {
        return vehicleService.searchAllByModel(model);
    }

    @GetMapping("/find/{brand}")
    public List<Vehicle> searchAllByBrand(@PathVariable String brand) {
        return vehicleService.searchAllByBrand(brand);
    }


    @GetMapping("/findBy/{licensePlate}")
    public List<Vehicle> findByLicensePlate(@PathVariable String licensePlate) {
        return vehicleService.findByLicensePlate(licensePlate);
    }

    @GetMapping("getBy/{creationYear}")
    public List<Vehicle> searchAllByCreationYear(@PathVariable Long creationYear) {
        return vehicleService.searchAllByCreationYear(creationYear);
    }

    @GetMapping("getAll/{VIN}")
    public List<Vehicle> searchAllByVIN(@PathVariable String VIN) {
        return vehicleService.searchAllByVIN(VIN);
    }


    @GetMapping("/{vehicleId}")
    public Vehicle getById(@PathVariable Long vehicleId) {
        try {
            return vehicleService.getById(vehicleId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/new")
    public Vehicle createVehicle(
            @RequestHeader("Authorization") HttpHeaders headers,
            @Valid @RequestBody VehicleDto vehicleDto
    ) {
        Vehicle vehicle;
        try {
            authenticationHelper.checkAuthorization(headers);
            vehicle = modelMapper.map(vehicleDto, Vehicle.class);
            vehicleService.save(vehicle);
            return vehicleService.getById(vehicle.getVehicleId());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


    @PutMapping("/{vehicleId}/update")
    public Vehicle updateVehicle(@PathVariable Long vehicleId,
                                 @Valid @RequestBody VehicleDto vehicleDto,
                                 @RequestHeader("Authorization") HttpHeaders headers) {
        Vehicle existingVehicle;
        try {
            authenticationHelper.checkAuthorization(headers);
            existingVehicle = vehicleService.getById(vehicleId);
            return vehicleService.update(existingVehicle, vehicleDto);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{vehicleId}")
    public void deleteVehicle(@PathVariable Long vehicleId,
                              @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            vehicleService.deleteVehicleById(vehicleId);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}