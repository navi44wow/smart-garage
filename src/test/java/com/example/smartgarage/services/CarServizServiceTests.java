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
        Mockito.when(carServiceRepository.findById(mockService.getId())).thenReturn(Optional.of(mockService));

        Optional<CarService> result = carService.findById(mockService.getId());

        assertTrue(result.isPresent());
        assertEquals(mockService.getId(), result.get().getId());
        Mockito.verify(carServiceRepository, Mockito.times(1)).findById(mockService.getId());
    }
}
