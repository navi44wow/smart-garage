package com.example.smartgarage.services;

import com.example.smartgarage.models.dtos.VehicleFilterDto;
import com.example.smartgarage.models.entities.*;
import com.example.smartgarage.repositories.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.smartgarage.Helpers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTests {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;


    @Test
    void getAll_Should_ReturnListOfObjects() {
        List<Vehicle> mockList = new ArrayList<>();
        mockList.add(createMockVehicle());
        mockList.add(createMockVehicle());

        Mockito.when(vehicleRepository.findAll()).thenReturn(mockList);
        List<Vehicle> result = vehicleService.getAll();

        assertEquals(mockList.size(), result.size());
        Mockito.verify(vehicleRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findById_Should_ReturnObject() {
        Vehicle vehicle = createMockVehicle();
        Mockito.when(vehicleRepository.findById(vehicle.getVehicleId())).thenReturn(Optional.of(vehicle));

        Optional<Vehicle> result = Optional.ofNullable(vehicleService.getById(vehicle.getVehicleId()));

        assertTrue(result.isPresent());
        assertEquals(vehicle.getVehicleId(), result.get().getVehicleId());
        Mockito.verify(vehicleRepository, Mockito.times(1)).findById(vehicle.getVehicleId());
    }


    @Test
    public void create_Should_returnVehicle_When_Valid() {

        vehicleService.save(createMockVehicle());
        Optional<Vehicle> mockVehicle = Optional.of(createMockVehicle());

        // Assert
        assertTrue(mockVehicle.isPresent());
    }


    @Test
    void getAllGeneric_Should_ReturnAllVehicles_When_NoFiltersApplied() {
        // Arrange
        Vehicle vehicle = createMockVehicle();
        Vehicle vehicle1 = createMockVehicle();
        List<Vehicle> mockVehicles = new ArrayList<>();
        mockVehicles.add(vehicle);
        mockVehicles.add(vehicle1);

        Mockito.when(vehicleRepository.findAll()).thenReturn(mockVehicles);
        // Act
        List<Vehicle> result = vehicleService.getAllVehicles(new VehicleFilterDto());
        // Assert
        assertEquals(mockVehicles, result);
    }


    @Test
    void searchAllByCreationYear_Should_ReturnListOfVehicles() {

        List<Vehicle> vehiclesList = new ArrayList<>();
        Vehicle vehicle = createMockVehicle();
        Vehicle vehicle1 = createMockVehicle();

        vehicle.setCreationYear(2000L);
        vehicle1.setCreationYear(2000L);

        vehiclesList.add(vehicle);
        vehiclesList.add(vehicle1);


        Mockito.when(vehicleRepository.searchAllByCreationYear(2000L)).thenReturn(vehiclesList);

        List<Vehicle> result = (vehicleService.searchAllByCreationYear(2000L));

        assertNotNull(result);
        assertEquals(vehiclesList.size(), result.size());
        Mockito.verify(vehicleRepository, Mockito.times(1)).searchAllByCreationYear(vehicle.getCreationYear());
    }

    @Test
    void searchAllByLicensePlate_Should_ReturnListOfVehicles() {

        List<Vehicle> vehiclesList = new ArrayList<>();
        Vehicle vehicle = createMockVehicle();
        Vehicle vehicle1 = createMockVehicle();

        vehicle.setLicensePlate("1212452");
        vehicle1.setLicensePlate("1212452");

        vehiclesList.add(vehicle);
        vehiclesList.add(vehicle1);

        Mockito.when(vehicleRepository.findByLicensePlate("1212452")).thenReturn(vehiclesList);

        List<Vehicle> result = (vehicleService.findByLicensePlate("1212452"));

        assertNotNull(result);
        assertEquals(vehiclesList.size(), result.size());
        Mockito.verify(vehicleRepository, Mockito.times(1)).findByLicensePlate(vehicle.getLicensePlate());
    }


    @Test
    void searchAllByCarModelId_Should_ReturnListOfVehicles() {

        List<Vehicle> vehiclesList = new ArrayList<>();
        Vehicle vehicle = createMockVehicle();
        Vehicle vehicle1 = createMockVehicle();

        CarModel carModel = createMockModel();
        vehicle.setCarModelId(carModel);
        vehicle1.setCarModelId(carModel);

        vehiclesList.add(vehicle);
        vehiclesList.add(vehicle1);

        Mockito.when(vehicleRepository.findAllByCarModelId(carModel)).thenReturn(vehiclesList);

        List<Vehicle> result = (vehicleService.findAllByCarModelId(carModel));

        assertNotNull(result);
        assertEquals(vehiclesList.size(), result.size());
        Mockito.verify(vehicleRepository, Mockito.times(1)).findAllByCarModelId(vehicle.getCarModelId());
    }


    @Test
    void getByUserId_Should_ReturnListOfVehicles() {

        List<Vehicle> vehiclesList = new ArrayList<>();
        Vehicle vehicle = createMockVehicle();
        Vehicle vehicle1 = createMockVehicle();

        User user = createMockUser();
        vehicle.setUser(user);
        vehicle1.setUser(user);

        vehiclesList.add(vehicle);
        vehiclesList.add(vehicle1);

        Mockito.when(vehicleRepository.findAllByUserId(user.getId())).thenReturn(vehiclesList);

        List<Vehicle> result = (vehicleService.getByUserId(user));

        assertNotNull(result);
        assertEquals(vehiclesList.size(), result.size());
        Mockito.verify(vehicleRepository, Mockito.times(1)).findAllByUserId(vehicle.getUser().getId());
    }

    @Test
    void getByUsername_Should_ReturnListOfVehicles() {

        List<Vehicle> vehiclesList = new ArrayList<>();
        Vehicle vehicle = createMockVehicle();
        Vehicle vehicle1 = createMockVehicle();

        User user = createMockUser();
        vehicle.setUser(user);
        vehicle1.setUser(user);

        vehiclesList.add(vehicle);
        vehiclesList.add(vehicle1);

        Mockito.when(vehicleRepository.getAllByUserUsername(user.getUsername())).thenReturn(vehiclesList);

        List<Vehicle> result = (vehicleService.getByUsername(user.getUsername()));

        assertNotNull(result);
        assertEquals(vehiclesList.size(), result.size());
        Mockito.verify(vehicleRepository, Mockito.times(1)).getAllByUserUsername(vehicle.getUser().getUsername());
    }


    @Test
    void searchAllByVIN_Should_ReturnListOfVehicles() {

        List<Vehicle> vehiclesList = new ArrayList<>();
        Vehicle vehicle = createMockVehicle();
        Vehicle vehicle1 = createMockVehicle();

        vehicle.setVIN("12345678987654321");
        vehicle1.setVIN(vehicle.getVIN());

        vehiclesList.add(vehicle);
        vehiclesList.add(vehicle1);

        Mockito.when(vehicleRepository.searchAllByVIN("12345678987654321")).thenReturn(vehiclesList);

        List<Vehicle> result = (vehicleService.searchAllByVIN("12345678987654321"));

        assertNotNull(result);
        assertEquals(vehiclesList.size(), result.size());
        Mockito.verify(vehicleRepository, Mockito.times(1)).searchAllByVIN("12345678987654321");
    }
}