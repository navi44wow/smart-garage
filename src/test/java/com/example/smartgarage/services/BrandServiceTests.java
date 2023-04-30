package com.example.smartgarage.services;

import com.example.smartgarage.models.entities.*;
import com.example.smartgarage.repositories.BrandRepository;
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
public class BrandServiceTests {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandServiceImpl brandService;


    @Test
    void getAll_Should_ReturnListOfObjects() {
        List<Brand> brandList = new ArrayList<>();
        brandList.add(createMockBrand());
        brandList.add(createMockBrand());

        Mockito.when(brandRepository.findAll()).thenReturn(brandList);
        List<Brand> result = brandService.getAll();

        assertEquals(brandList.size(), result.size());
        Mockito.verify(brandRepository, Mockito.times(1)).findAll();
    }


    @Test
    void getByBrandId_Should_ReturnObject() {

        Brand brand = createMockBrand();

        Mockito.when(brandRepository.findById(brand.getId())).thenReturn(Optional.of(brand));

        Optional<Brand> result = Optional.ofNullable(brandService.getById(brand.getId()));

        assertTrue(result.isPresent());
        assertEquals(brand.getId(), result.get().getId());
        Mockito.verify(brandRepository, Mockito.times(1)).findById(brand.getId());
    }

    @Test
    void getByBrandName_Should_ReturnObject() {

        Brand brand = createMockBrand();

        Mockito.when(brandRepository.findByBrandName(brand.getBrandName())).thenReturn(Optional.of(brand));

        Optional<Brand> result = Optional.ofNullable(brandService.getByName(brand.getBrandName()));

        assertTrue(result.isPresent());
        assertEquals(brand.getBrandName(), result.get().getBrandName());
        Mockito.verify(brandRepository, Mockito.times(1)).findByBrandName(brand.getBrandName());
    }
}
