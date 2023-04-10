package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {


    List<Vehicle> searchAllByModel(String model);


    List<Vehicle> searchAllByBrand(String brand);

    List<Vehicle> searchAllByCreationYear(Long creationYear);

    List<Vehicle> findByLicensePlate(String licensePlate);
}
