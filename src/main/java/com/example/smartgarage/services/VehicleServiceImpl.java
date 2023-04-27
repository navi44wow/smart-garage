package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.entities.CarModel;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.repositories.VehicleRepository;
import com.example.smartgarage.services.contracts.VehicleService;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {


    private final SessionFactory sessionFactory;

    private final ModelMapper modelMapper;

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(SessionFactory sessionFactory, ModelMapper modelMapper, VehicleRepository vehicleRepository) {
        this.sessionFactory = sessionFactory;
        this.modelMapper = modelMapper;
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

    @Override
    public List<Vehicle> searchAllByCarModelId(CarModel carModel) {
        return vehicleRepository.findAllByCarModelId(carModel);
    }

    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle update(Vehicle vehicle, VehicleDto vehicleDto, CarModel carModel) {
        vehicle.setVIN(vehicleDto.getVIN());
        vehicle.setUser(vehicle.getUser());
        vehicle.setCreationYear(vehicleDto.getCreationYear());
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    @Override
    public Vehicle updateForMVC(Vehicle vehicle, VehicleDto vehicleDto) {
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public void deleteVehicleById(Long vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }

    public void deleteVehicleByVehicleId(Long vehicleId) {
        vehicleRepository.deleteVehicleByVehicleId(vehicleId);
    }

    public Vehicle getById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId).orElseThrow(() ->
                new EntityNotFoundException("Vehicle with id ", vehicleId.toString(), " was not found!"));
    }

    @Override
    public List<Vehicle> findAllByCarModelId(CarModel carModel) {
        return vehicleRepository.findAllByCarModelId(carModel);
    }

    public List<Vehicle> getByUserId(User userId) {
        return vehicleRepository.findAllByUserId(userId.getId());
    }

    @Override
    public List<Vehicle> getByUsername(String username) {
        return vehicleRepository.getAllByUserUsername((username));
    }

    @Override
    public <T> List<Vehicle> getAllGenericVehicles(Optional<T> creationYear, Optional<T> sortBy, Optional<T> sortOrder) {


        List<Vehicle> vehicles;

        if (creationYear.isPresent() && !creationYear.get().toString().isBlank()) {
            vehicles = vehicleRepository.searchAllByCreationYear(Long.valueOf(creationYear.get().toString()));
        } else {
            vehicles = vehicleRepository.findAll();
        }
        if (sortBy.isPresent() && !sortBy.get().toString().isBlank()) {
            String sortByValue = sortBy.get().toString();
            Sort.Direction sortDirection = Sort.Direction.ASC;
            if (sortOrder.isPresent() && sortOrder.get().toString().equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
            Sort sort = Sort.by(sortDirection, sortByValue);
            vehicles = vehicleRepository.findAll(sort);
        }
        return vehicles;
    }

}