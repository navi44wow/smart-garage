package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.dtos.VehicleFilterDto;
import com.example.smartgarage.models.entities.CarModel;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    Vehicle getById(Long vehicleId);

    List<Vehicle> findAllByCarModelId(CarModel carModel);

    List<Vehicle> getByUserId(User userId);

    void save(Vehicle vehicle);

    Vehicle update(Vehicle vehicle, VehicleDto vehicleDto );

    void deleteVehicleById(Long vehicleId);

    List<Vehicle> getAll();

    Optional<Vehicle> findByLicensePlate(String licensePlate);

    List<Vehicle> searchAllByCreationYear(Long creationYear);

    List<Vehicle> getByUsername(String username);

    List<Vehicle> getAllVehicles(
            VehicleFilterDto vehicleFilterDto);

    Optional<Vehicle> getByVIN(String VIN);
}