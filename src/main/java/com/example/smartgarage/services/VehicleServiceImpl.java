package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.dtos.CarServiceDto;
import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.entities.CarService;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.repositories.CarServiceRepository;
import com.example.smartgarage.repositories.VehicleRepository;
import com.example.smartgarage.services.contracts.VehicleService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {


    private final SessionFactory sessionFactory;

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(SessionFactory sessionFactory, VehicleRepository vehicleRepository) {
        this.sessionFactory = sessionFactory;
        this.vehicleRepository = vehicleRepository;
    }


    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public int getVehiclesCount() {
        return 0;
    }

    @Override
    public List<Vehicle> searchAllByModel(String model) {
        return vehicleRepository.searchAllByModel(model);
    }

    @Override
    public List<Vehicle> searchAllByBrand(String brand) {
        return vehicleRepository.searchAllByBrand(brand);
    }

    @Override
    public List<Vehicle> findByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate);
    }

    @Override
    public List<Vehicle> searchAllByCreationYear(Long creationYear) {
        return vehicleRepository.searchAllByCreationYear(creationYear);
    }

    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public Vehicle update(Vehicle vehicle, VehicleDto vehicleDto) {
        vehicle.setVIN(vehicle.getVIN());
        vehicle.setBrand(vehicleDto.getBrand());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setCreationYear(vehicleDto.getCreationYear());
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public void deleteVehicleById(Long vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }

    public Vehicle getById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId).orElseThrow(() ->
                new EntityNotFoundException("Vehicle with id ", vehicleId.toString(), " was not found!"));
    }
}
