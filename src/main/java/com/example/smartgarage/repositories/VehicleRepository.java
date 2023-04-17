package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> searchAllByCreationYear(Long creationYear);

    List<Vehicle> findByLicensePlate(String licensePlate);

    List<Vehicle> searchAllByVIN(String VIN);

    List<Vehicle> findAllByUserId(User user);
}
