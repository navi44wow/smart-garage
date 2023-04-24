package com.example.smartgarage.services;


import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.entities.CarModel;
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

    public Vehicle createDtoToObject(VehicleDto vehicleDto) {
        User user = new User();
        user.setId(vehicleDto.getUser());
        CarModel carModel = new CarModel();
        carModel.setModelId(vehicleDto.getModel());
        String VIN = (vehicleDto.getVIN());
        Long creationYear = vehicleDto.getCreationYear();
        String licensePlate = vehicleDto.getLicensePlate();
        //VIN =
        Vehicle vehicle = new Vehicle();
        vehicle.setUser(user);
        vehicle.setCarModelId(carModel);
        vehicle.setVIN(VIN);
        vehicle.setCreationYear(creationYear);
        vehicle.setLicensePlate(licensePlate);
//        vehicle.setVIN(vehicleDto.getVIN());
//        vehicle.setCreationYear(vehicleDto.getCreationYear());
//        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        return vehicle;
    }
}