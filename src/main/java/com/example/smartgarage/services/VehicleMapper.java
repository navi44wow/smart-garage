package com.example.smartgarage.services;


import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.repositories.VehicleRepository;
import com.example.smartgarage.services.contracts.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;

    @Autowired
    public VehicleMapper(VehicleRepository vehicleRepository, VehicleService vehicleService) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleService = vehicleService;
    }

    public Vehicle createDtoToObject(VehicleDto vehicleDto,User user) {
        Vehicle vehicle = new Vehicle();
        //vehicle.setUser(vehicleDto.getUser());
     //   vehicle.setModelId(vehicleDto.getModel());
        vehicle.setVIN(vehicleDto.getVIN());
        vehicle.setCreationYear(vehicleDto.getCreationYear());
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        return vehicle;
    }
}