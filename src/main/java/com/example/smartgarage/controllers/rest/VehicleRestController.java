package com.example.smartgarage.controllers.rest;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;

import com.example.smartgarage.models.entities.Brand;
import com.example.smartgarage.models.entities.Model;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.VehicleMapper;
import com.example.smartgarage.services.contracts.BrandService;
import com.example.smartgarage.services.contracts.ModelService;
import com.example.smartgarage.services.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ResponseStatusException;

import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.services.contracts.VehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/vehicles")

public class VehicleRestController {

    private final VehicleService vehicleService;
    private final BrandService brandService;
    private final ModelService modelService;
    private final ModelMapper modelMapper;

    private final VehicleMapper vehicleMapper;
    private final AuthenticationHelper authenticationHelper;

    private final UserService userService;

    public VehicleRestController(VehicleService vehicleService, BrandService brandService, ModelService modelService, ModelMapper modelMapper
            , VehicleMapper vehicleMapper, AuthenticationHelper authenticationHelper,
                                 UserService userService) {
        this.vehicleService = vehicleService;
        this.brandService = brandService;
        this.modelService = modelService;
        this.modelMapper = modelMapper;
        this.vehicleMapper = vehicleMapper;
        this.authenticationHelper = authenticationHelper;
        this.userService = userService;
    }

    @GetMapping()
    public List<Vehicle> getAll(@RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return vehicleService.getAll();
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/vehicleId/{id}")
    public Vehicle getById(@PathVariable Long id) {
        try {
            return vehicleService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/userId/{userId}")
    public List<Vehicle> getByUserId(@PathVariable User userId, @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return vehicleService.getByUserId(userId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/username/{username}")
    public List<Vehicle> getByUsername(@PathVariable String username, @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return vehicleService.getByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/phone/{phone}")
    public List<Vehicle> getByUserPhoneNumber(@PathVariable String phone, @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            UserViewModel user = userService.getByPhoneNumber(phone);
            return vehicleService.getByUsername((user.getUsername()));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @GetMapping("/model/{modelName}")
    public List<Vehicle> getAllByModelName(@PathVariable String modelName, @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            Optional<Model> model = modelService.findByModelName(modelName);
            return vehicleService.searchAllByModelId(model.get());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/brand/{brandName}")
    public List<Vehicle> getAllByBrandName(@PathVariable String brandName, @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            Brand brand = brandService.findByBrandName(brandName).orElseThrow(IllegalArgumentException::new);
            List<Model> models = modelService.getByBrandId(brand.getId());
            List<Vehicle> vehiclesNew = new ArrayList<>();
            for (Model m : models) {

                vehiclesNew.addAll(vehicleService.findAllByModelId(m));
            }
            return vehiclesNew;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @GetMapping("/licensePlate/{licensePlate}")
    public List<Vehicle> findByLicensePlate(@PathVariable String licensePlate) {
        return vehicleService.findByLicensePlate(licensePlate);
    }

    @GetMapping("/creationYear/{creationYear}")
    public List<Vehicle> searchAllByCreationYear(@PathVariable Long creationYear) {
        return vehicleService.searchAllByCreationYear(creationYear);
    }

    @GetMapping("/VIN/{VIN}")
    public List<Vehicle> searchAllByVIN(@PathVariable String VIN) {
        return vehicleService.searchAllByVIN(VIN);
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
            vehicle.setUser(userService.getById(vehicleDto.getUser()));
            vehicle.setModelId(modelService.getById(vehicleDto.getModel()));
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
                                 @RequestHeader("Authorization") HttpHeaders headers, Model model) {
        Vehicle existingVehicle = vehicleService.getById(vehicleId);
        try {
            authenticationHelper.checkAuthorization(headers);
            vehicleService.save(existingVehicle);
            existingVehicle.setVIN(vehicleDto.getVIN());
            existingVehicle.setCreationYear(vehicleDto.getCreationYear());
            existingVehicle.setLicensePlate(vehicleDto.getLicensePlate());
            existingVehicle.setUser(userService.getById(vehicleDto.getUser()));
            existingVehicle.setModelId(modelService.getById(vehicleDto.getModel()));
            return existingVehicle;
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