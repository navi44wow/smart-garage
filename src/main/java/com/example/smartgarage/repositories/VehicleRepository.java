package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.CarModel;
import com.example.smartgarage.models.entities.Vehicle;

import com.example.smartgarage.models.view_models.UserViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    void deleteVehicleByVehicleId(Long vehicleId);
    List<Vehicle> searchAllByCreationYear(Long creationYear);

    List<Vehicle> findByLicensePlate(String licensePlate);

    List<Vehicle> searchAllByVIN(String VIN);

    List<Vehicle> findAllByCarModelId(CarModel carModel);

    List<Vehicle> findAllByUserId(UUID user);

    List<Vehicle> getAllByUserUsername(String username);

    Optional<Vehicle> findById(Long vehicleId);

    @Query("SELECT v FROM Vehicle v WHERE v.user = :user")
    List<Vehicle> getByOwner(@Param("user") UserViewModel user);

}
