package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.*;
import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    Vehicle getById(Long vehicleId);

    void save(Vehicle vehicle);

    public Vehicle update(Vehicle vehicle, VehicleDto vehicleDto);

    void deleteVehicleById(Long vehicleId);

    List<Vehicle> getAll();

    int getVehiclesCount();

    List<Vehicle> searchAllByModel(String model);

    List<Vehicle> searchAllByBrand(String brand);


    List<Vehicle> findByLicensePlate(String licensePlate);

    List<Vehicle> searchAllByCreationYear(Long creationYear);

}