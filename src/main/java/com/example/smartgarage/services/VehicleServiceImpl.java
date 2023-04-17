package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.entities.Model;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.repositories.VehicleRepository;
import com.example.smartgarage.services.contracts.VehicleService;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Vehicle> findByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate);
    }

    @Override
    public List<Vehicle> searchAllByCreationYear(Long creationYear) {
        return vehicleRepository.searchAllByCreationYear(creationYear);
    }

    @Override
    public List<Vehicle> searchAllByVIN(String VIN) {
        return vehicleRepository.searchAllByVIN(VIN);
    }


    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public Vehicle update(Vehicle vehicle, VehicleDto vehicleDto, Model model) {
        vehicle.setVIN(vehicleDto.getVIN());
        vehicle.setUser(vehicle.getUser());
        vehicle.setModelId(vehicleDto.getModelId());
        model.setBrand(vehicleDto.getModelId().getBrand());
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

    public List<Vehicle> getByUserId(User user) {
        return vehicleRepository.findAllByUserId(user);
    }

}
