package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.entities.CarModel;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;

import java.util.List;
import java.util.Optional;


public interface VehicleService {

    Vehicle getById(Long vehicleId);

    List<Vehicle> findAllByCarModelId(CarModel carModel);

    List<Vehicle> getByUserId(User userId);

    List<Vehicle> searchAllByCarModelId(CarModel carModel);

    void save(Vehicle vehicle);

    Vehicle update(Vehicle vehicle, VehicleDto vehicleDto, CarModel carModel);

    void deleteVehicleById(Long vehicleId);

    List<Vehicle> getAll();

    int getVehiclesCount();

    List<Vehicle> findByLicensePlate(String licensePlate);

    List<Vehicle> searchAllByCreationYear(Long creationYear);

    List<Vehicle> searchAllByVIN(String VIN);

    List<Vehicle> getByUsername(String username);

    <T> List<Vehicle> getAllGenericVehicles(Optional<T> brand,Optional<T> carModel,Optional<T> user,Optional<T> creationYearMin,Optional<T> creationYearMax, Optional<T> sortBy, Optional<T> sortOrder);


}