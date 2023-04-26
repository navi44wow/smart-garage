package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.dtos.BrandDto;
import com.example.smartgarage.models.entities.Brand;
import com.example.smartgarage.repositories.BrandRepository;
import com.example.smartgarage.services.contracts.BrandService;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BrandServiceImpl implements BrandService {
    private final SessionFactory sessionFactory;

    private final BrandRepository brandRepository;

    public BrandServiceImpl(SessionFactory sessionFactory, BrandRepository brandRepository) {
        this.sessionFactory = sessionFactory;
        this.brandRepository = brandRepository;
    }


    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getById(Long id) {
        return brandRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Brand with id ", id.toString(), " was not found!"));
    }

    @Override
    public Brand getByName(String brandName) {
        return brandRepository.findByBrandName(brandName).orElseThrow(() ->
                new EntityNotFoundException("Brand with name ", brandName, " was not found!"));
    }

    @Override
    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public Brand update(Brand brand, BrandDto brandDto) {
        brand.setBrandName(brandDto.getBrandName());
        brandRepository.save(brand);
        return brand;
    }

    @Override
    public void deleteBrandById(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public int getBrandsCount() {
        return 0;
    }

    @Override
    public Optional<Brand> findByBrandName(String brandName) {
        return brandRepository.findByBrandName(brandName);
    }

    @Override
    public <T> List<Brand> getAllGeneric(Optional<T> brandName, Optional<T> sortBy, Optional<T> sortOrder) {
        List<Brand> brands;
        if (brandName.isPresent() && !brandName.get().toString().isBlank()) {
            brands = brandRepository.findByBrandNameContainingIgnoreCase(brandName.toString());
        } else {
            brands = brandRepository.findAll();
        }
        if (sortBy.isPresent() && !sortBy.get().toString().isBlank()) {
            String sortByValue = sortBy.get().toString();
            Sort.Direction sortDirection = Sort.Direction.ASC;
            if (sortOrder.isPresent() && sortOrder.get().toString().equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
            Sort sort = Sort.by(sortDirection, sortByValue);
            brands = brandRepository.findAll(sort);
        }
        return brands;
    }
}
