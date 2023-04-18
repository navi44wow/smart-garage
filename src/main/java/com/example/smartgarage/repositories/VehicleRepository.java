package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.Model;
import com.example.smartgarage.models.entities.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> searchAllByCreationYear(Long creationYear);

    List<Vehicle> findByLicensePlate(String licensePlate);

    List<Vehicle> searchAllByVIN(String VIN);

    List<Vehicle> findAllByModelId(Model model);

    List<Vehicle> findAllByUserId(UUID user);

    Optional<Vehicle> findById(Long vehicleId);
}
