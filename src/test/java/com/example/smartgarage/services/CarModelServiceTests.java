package com.example.smartgarage.services;

import com.example.smartgarage.models.entities.*;
import com.example.smartgarage.repositories.CarModelRepository;
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
public class CarModelServiceTests {


    @Mock
    private CarModelRepository carModelRepository;

    @InjectMocks
    private CarModelServiceImpl carModelService;


    @Test
    void getAll_Should_ReturnListOfObjects() {
        List<CarModel> carModelList = new ArrayList<>();
        carModelList.add(createMockModel());
        carModelList.add(createMockModel());

        Mockito.when(carModelRepository.findAll()).thenReturn(carModelList);
        List<CarModel> result = carModelService.getAll();

        assertEquals(carModelList.size(), result.size());
        Mockito.verify(carModelRepository, Mockito.times(1)).findAll();
    }


    @Test
    void getByBrandName_Should_ReturnListOfObjects() {
        List<CarModel> carModelList = new ArrayList<>();
        CarModel carModel = createMockModel();
        CarModel carModel1 = createMockModel();
        carModel1.setBrand(carModel.getBrand());
        carModelList.add(carModel);
        carModelList.add(carModel1);

        Mockito.when(carModelRepository.searchAllByBrand_BrandName(carModel1.getBrand().getBrandName())).thenReturn(carModelList);
        List<CarModel> result = carModelService.getByBrandName(carModel1.getBrand().getBrandName());

        assertEquals(carModelList.size(), result.size());
        Mockito.verify(carModelRepository, Mockito.times(1)).searchAllByBrand_BrandName(carModel.getBrand().getBrandName());
    }


    @Test
    void getByBrandId_Should_ReturnListOfObjects() {
        List<CarModel> carModelList = new ArrayList<>();
        CarModel carModel = createMockModel();
        CarModel carModel1 = createMockModel();
        carModel1.setBrand(carModel.getBrand());
        carModelList.add(carModel);
        carModelList.add(carModel1);

        Mockito.when(carModelRepository.findAllByBrandId(carModel1.getBrand().getId())).thenReturn(carModelList);
        List<CarModel> result = carModelService.getByBrandId(carModel1.getBrand().getId());

        assertEquals(carModelList.size(), result.size());
        Mockito.verify(carModelRepository, Mockito.times(1)).findAllByBrandId(carModel.getBrand().getId());
    }


    @Test
    void getByCarModelName_Should_ReturnObject() {

        CarModel carModel = createMockModel();

        Mockito.when(carModelRepository.findByModelName(carModel.getModelName())).thenReturn(Optional.of(carModel));

        Optional<CarModel> result = Optional.ofNullable(carModelService.getByName(carModel.getModelName()));

        assertTrue(result.isPresent());
        assertEquals(carModel.getModelName(), result.get().getModelName());
        Mockito.verify(carModelRepository, Mockito.times(1)).findByModelName(carModel.getModelName());
    }
}