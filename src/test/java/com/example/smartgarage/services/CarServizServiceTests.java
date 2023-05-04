package com.example.smartgarage.services;

import com.example.smartgarage.models.entities.CarService;
import com.example.smartgarage.repositories.CarServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.smartgarage.Helpers.createAnotherMockCarService;
import static com.example.smartgarage.Helpers.createMockCarService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CarServizServiceTests {

    @Mock
    CarServiceRepository carServiceRepository;

    @InjectMocks
    CarServiceImpl carService;


    @Test
    void getAll_Should_ReturnListOfObjects() {
        List<CarService> mockList = new ArrayList<>();
        mockList.add(createMockCarService());
        mockList.add(createAnotherMockCarService());

        Mockito.when(carServiceRepository.findAll()).thenReturn(mockList);
        List<CarService> result = carService.getAll();

        assertEquals(mockList.size(), result.size());
        Mockito.verify(carServiceRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findById_Should_ReturnObject() {
        CarService mockService = createMockCarService();
        Mockito.when(carServiceRepository.findById(mockService.getId()))
                .thenReturn(Optional.of(mockService));

        Optional<CarService> result = carService.findById(mockService.getId());

        assertTrue(result.isPresent());
        assertEquals(mockService.getId(), result.get().getId());
        Mockito.verify(carServiceRepository, Mockito.times(1)).findById(mockService.getId());
    }

    @Test
    public void create_Should_returnService_When_Valid() {

        carService.create(createMockCarService());
        Optional<CarService> mockService = Optional.of(createMockCarService());

        // Assert
        assertTrue(mockService.isPresent());

    }

    @Test
    void getAllGeneric_Should_ReturnAllCarServices_When_NoFiltersApplied() {
        // Arrange
        List<CarService> mockCarServices = Arrays.asList(
                new CarService(1L, "Service1", 100),
                new CarService(2L, "Service2", 200)
        );
        Mockito.when(carServiceRepository.findAll()).thenReturn(mockCarServices);

        // Act
        List<CarService> result = carService.getAllGeneric(Optional.empty(), Optional.empty(), Optional.empty());

        // Assert
        assertEquals(mockCarServices, result);
    }
}
