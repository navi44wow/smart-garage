package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.entities.CarModel;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.repositories.VehicleRepository;
import com.example.smartgarage.services.contracts.VehicleService;
import org.hibernate.SessionFactory;
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
    public List<Vehicle> searchAllByModelId(CarModel carModel) {
        return vehicleRepository.findAllByCarModelId(carModel);
    }

    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle update(Vehicle vehicle, VehicleDto vehicleDto, CarModel carModel) {
        vehicle.setVIN(vehicleDto.getVIN());
        vehicle.setUser(vehicle.getUser());
        // vehicle.setModelId(vehicleDto.getModel());
        //    model.setBrand(vehicleDto.getModel().getBrand());
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

    @Override
    public List<Vehicle> findAllByModelId(CarModel carModel) {
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
    public <T> List<Vehicle> getAllGenericVehicles(Optional<T> brand, Optional<T> model, Optional<T> user, Optional<T> creationYearMin, Optional<T> creationYearMax, Optional<T> sortBy, Optional<T> sortOrder) {
        return null;
    }


//        List<Vehicle> vehicles;
//        if (brand.isPresent() && !brand.get().toString().isBlank()) {
//            vehicles = vehicleRepository.findAllByModelId_BrandIgnoreCase(brand);
//        } else {
//            vehicles = vehicleRepository.findAll();
//        }
//
//        if (model.isPresent() && !model.get().toString().isBlank()) {
//            vehicles = vehicleRepository.findAllByModelId_IgnoreCase(model);
//        } else {
//            vehicles = vehicleRepository.findAll();
//        }
//
//        if (brand.isPresent() && !brand.get().toString().isBlank()) {
//            vehicles = vehicleRepository.findAllByModelId_BrandIgnoreCase(brand);
//        } else {
//            vehicles = vehicleRepository.findAll();
//        }


//        if (sortBy.isPresent() && !sortBy.get().toString().isBlank()) {
//            String sortByValue = sortBy.get().toString();
//            Sort.Direction sortDirection = Sort.Direction.ASC;
//            if (sortOrder.isPresent() && sortOrder.get().toString().equalsIgnoreCase("desc")) {
//                sortDirection = Sort.Direction.DESC;
//            }
//            Sort sort = Sort.by(sortDirection, sortByValue);
//            vehicles = vehicleRepository.findAll(sort);
//        }
//
//        return vehicles;
//    }
//}

    /*
    public <T> List<CarService> getAllGeneric(Optional<T> name, Optional<T> sortBy, Optional<T> sortOrder) {
        List<CarService> carServices;
        if (name.isPresent() && !name.get().toString().isBlank()) {
            carServices = carServiceRepository.findByNameContainingIgnoreCase(name.get().toString());
        } else {
            carServices = carServiceRepository.findAll();
        }

        if (sortBy.isPresent() && !sortBy.get().toString().isBlank()) {
            String sortByValue = sortBy.get().toString();
            Sort.Direction sortDirection = Sort.Direction.ASC;
            if (sortOrder.isPresent() && sortOrder.get().toString().equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
            Sort sort = Sort.by(sortDirection, sortByValue);
            carServices = carServiceRepository.findAll(sort);
        }

        return carServices;
    }
     */
}