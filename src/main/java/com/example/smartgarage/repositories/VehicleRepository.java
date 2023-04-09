package com.example.smartgarage.repositories;


import com.example.smartgarage.models.entities.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {


    List<Vehicle> getAll();

    List<Vehicle> getVehiclesByUserId(Long id);

    Vehicle getById(Long vehicleId);

    void create(Vehicle vehicle);

    void update(Vehicle vehicle);

    void delete(Long vehicle_id);

    //List<Vehicle> getVehiclesFilterVehicleOptions(VehicleFilterOptions filterVehicleOptions);

    List<Vehicle> filter(Optional<Long> vehicleId,
                         Optional<String> VIN,
                         Optional<String> license_plate,
                         Optional<String> model,
                         Optional<String> brand,
                         Optional<String> sort);



    List<Vehicle> getAll(Optional<String> search);

    int getVehiclesCount();

    //List<Vehicle> get(VehicleFilterOptions vehicleFilterOptions);
}