package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.models.dtos.CarModelDto;

import com.example.smartgarage.models.entities.CarModel;
import com.example.smartgarage.repositories.BrandRepository;
import com.example.smartgarage.repositories.CarModelRepository;
import com.example.smartgarage.services.contracts.CarModelService;

import java.util.List;
import java.util.Optional;

import com.example.smartgarage.exceptions.EntityNotFoundException;

import org.hibernate.SessionFactory;

import org.springframework.stereotype.Service;


@Service
public class CarModelServiceImpl implements CarModelService {

    private final SessionFactory sessionFactory;

    private final CarModelRepository carModelRepository;

    private final BrandRepository brandRepository;

    public CarModelServiceImpl(SessionFactory sessionFactory, CarModelRepository carModelRepository, BrandRepository brandRepository) {
        this.sessionFactory = sessionFactory;
        this.carModelRepository = carModelRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public List<CarModel> getAll() {
        return carModelRepository.findAll();
    }

    @Override
    public CarModel getById(Long carModelId) {
        return carModelRepository.findById(carModelId).orElseThrow(() ->
                new EntityNotFoundException("Model with id ", carModelId.toString(), " was not found!"));
    }

    @Override
    public CarModel getByName(String carModelName) {
        return carModelRepository.findByModelName(carModelName).orElseThrow(() ->
                new EntityNotFoundException("Model with name ", carModelName, " was not found!"));
    }

    @Override
    public List<CarModel> getByBrandName(String brandName) {
        return carModelRepository.searchAllByBrand_BrandName(brandName);
    }

    @Override
    public List<CarModel> getByBrandId(Long brandId) {
        return carModelRepository.findAllByBrandId(brandId);
    }

    @Override
    public void save(CarModel carModel) {
        carModelRepository.findByModelName(carModel.getModelName())
                .ifPresent(existing -> {
                    throw new EntityDuplicateException("Car model", "name", carModel.getModelName());
                });
        carModelRepository.save(carModel);
    }

    @Override
    public CarModel update(CarModel carModel, CarModelDto carModelDto) {
        carModel.setModelName(carModelDto.getModelName());
        carModel.setBrand(carModelDto.getBrand());
        carModelRepository.save(carModel);
        return carModel;
    }

    @Override
    public void deleteCarModelById(Long carModelId) {
        carModelRepository.deleteById(carModelId);
    }

    @Override
    public int getModelsCount() {
        return 0;
    }

    @Override
    public Optional<CarModel> findByCarModelName(String carModelName) {
        return carModelRepository.findByModelName(carModelName);
    }
}
