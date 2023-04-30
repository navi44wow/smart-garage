package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.dtos.BrandDto;
import com.example.smartgarage.models.entities.Brand;


import java.util.List;
import java.util.Optional;

public interface BrandService {


    List<Brand> getAll();

    Brand getById(Long brandId);

    Brand getByName(String brandName);

    void save(Brand brand);

    public Brand update(Brand brand, BrandDto brandDto);

    void deleteBrandById(Long brandId);

    int getBrandsCount();

    <T> List<Brand> getAllGeneric(Optional<T> brandName, Optional<T> sortBy, Optional<T> sortOrder);
}
