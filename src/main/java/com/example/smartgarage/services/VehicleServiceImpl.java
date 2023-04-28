package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.models.dtos.VehicleFilterDto;
import com.example.smartgarage.models.entities.CarModel;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.repositories.VehicleRepository;
import com.example.smartgarage.services.contracts.VehicleService;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Vehicle> getAllVehicles(
            VehicleFilterDto vehicleFilterDto) {
        List<Vehicle> allVehicles = getAllSorted(vehicleFilterDto.getSortBy(), vehicleFilterDto.getSortOrder());
        if (vehicleFilterDto.getBrandName() != null && !vehicleFilterDto.getBrandName().isEmpty()) {
            allVehicles = filterVehiclesByBrandName(allVehicles, vehicleFilterDto.getBrandName());
        }
        if (vehicleFilterDto.getCarModelName() != null && !vehicleFilterDto.getCarModelName().isEmpty()) {
            allVehicles = filterVehiclesByCarModelName(allVehicles, vehicleFilterDto.getCarModelName());
        }
        if (vehicleFilterDto.getCreationYear() != null) {
            allVehicles = filterVehiclesByCreationYear(allVehicles, vehicleFilterDto.getCreationYear());
        }
        if (vehicleFilterDto.getUsername() != null && !vehicleFilterDto.getUsername().isEmpty()) {
            allVehicles = filterVehiclesByUsername(allVehicles, vehicleFilterDto.getUsername());
        }
        return allVehicles;
    }

    private List<Vehicle> filterVehiclesByBrandName(List<Vehicle> vehicles, String brandName) {
        return vehicles.stream().filter(vehicle -> vehicle.getCarModelId().getBrand().getBrandName().
                equalsIgnoreCase(brandName)).collect(Collectors.toList());
    }

    private List<Vehicle> filterVehiclesByCarModelName(List<Vehicle> vehicles, String carModelName) {
        return vehicles.stream().filter(vehicle -> vehicle.getCarModelId().getModelName().
                equalsIgnoreCase(carModelName)).collect(Collectors.toList());
    }

    private List<Vehicle> filterVehiclesByCreationYear(List<Vehicle> vehicles, Long creationYear) {
        return vehicles.stream().filter(vehicle -> vehicle.getCreationYear().
                equals(creationYear)).collect(Collectors.toList());
    }

    private List<Vehicle> filterVehiclesByUsername(List<Vehicle> vehicles, String username) {
        return vehicles.stream().filter(vehicle -> vehicle.getUser().getUsername().
                equalsIgnoreCase(username)).collect(Collectors.toList());
    }


    private List<Vehicle> getAllSorted(String sortBy, String sortOrder) {
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        if (sortBy != null && !sortBy.equals("none") && sortOrder != null &&
                (sortOrder.equals("asc") || sortOrder.equals("desc"))) {
            Comparator<Vehicle> comparator = null;
            switch (sortBy) {
                case "brandName":
                    comparator = Comparator.comparing(v -> v.getCarModelId().getBrand().getBrandName());
                    break;
                case "carModelName":
                    comparator = Comparator.comparing(v -> v.getCarModelId().getModelName());
                    break;
                case "creationYear":
                    comparator = Comparator.comparing(Vehicle::getCreationYear);
                    break;
                case "username":
                    comparator = Comparator.comparing(v -> v.getUser().getUsername());
                    break;
            }
            if (comparator != null) {
            if (sortOrder.equals("asc")) {
                    allVehicles.sort(comparator);
                } else {
                    allVehicles.sort(comparator.reversed());
                }
            }
        }
        return allVehicles;
    }
}