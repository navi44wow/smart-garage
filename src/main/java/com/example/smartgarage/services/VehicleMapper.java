package com.example.smartgarage.services;


import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.repositories.VehicleRepository;
import com.example.smartgarage.services.contracts.ModelService;
import com.example.smartgarage.services.contracts.UserService;
import com.example.smartgarage.services.contracts.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;
    private final UserService userService;
    private final ModelService modelService;

    @Autowired
    public VehicleMapper(VehicleRepository vehicleRepository, VehicleService vehicleService, UserService userService, ModelService modelService) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.modelService = modelService;
    }

    public Vehicle createDtoToObject(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        //Vehicle vehicle = vehicleService.getById(vehicleDto.getve)
        //User user = userService.getById(vehicleDto.getUser());
        //Model model = modelService.getById(vehicleDto.getModel());

        vehicle.setUser(userService.getById(vehicleDto.getUser()));
        vehicle.setCarModelId(modelService.getById(vehicleDto.getModel()));

        vehicle.setVIN(vehicleDto.getVIN());
        vehicle.setCreationYear(vehicleDto.getCreationYear());
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        return vehicle;
    }
}