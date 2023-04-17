package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.entities.Model;

import com.example.smartgarage.models.entities.Vehicle;

import java.util.List;


public interface VehicleService {

    Vehicle getById(Long vehicleId);


    void save(Vehicle vehicle);

    public Vehicle update(Vehicle vehicle, VehicleDto vehicleDto, Model model);

    void deleteVehicleById(Long vehicleId);

    List<Vehicle> getAll();

    int getVehiclesCount();


    List<Vehicle> findByLicensePlate(String licensePlate);

    List<Vehicle> searchAllByCreationYear(Long creationYear);

    List<Vehicle> searchAllByVIN(String VIN);

}