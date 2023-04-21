package com.example.smartgarage.services;

import com.example.smartgarage.models.dtos.ModelDto;

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
    public CarModel getById(Long modelId) {
        return carModelRepository.findById(modelId).orElseThrow(() ->
                new EntityNotFoundException("Model with id ", modelId.toString(), " was not found!"));
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
        carModelRepository.save(carModel);

    }

    @Override
    public CarModel update(CarModel carModel, ModelDto modelDto) {
        carModel.setModelName(modelDto.getModelName());
        carModel.setBrand(modelDto.getBrand());
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


    //@Override
//    public <T> List<Model> getAllGeneric(Optional<T> brandName, Optional<T> model, Optional<T> sortBy, Optional<T> sortOrder) {
//        List<Model> models;
//        if (brandName.isPresent() && !brandName.get().toString().isBlank()) {
//            models = modelRepository.searchAllByBrand_BrandName(brandName.toString());
//        } else {
//            models = modelRepository.findAll();
//        }
//
//        if (model.isPresent() && !model.get().toString().isBlank()) {
//            models = modelRepository.findByModelName(model.toString()));
//        } else {
//            models = modelRepository.findAll();
//        }
//
//        if (sortBy.isPresent() && !sortBy.get().toString().isBlank()) {
//            String sortByValue = sortBy.get().toString();
//            Sort.Direction sortDirection = Sort.Direction.ASC;
//            if (sortOrder.isPresent() && sortOrder.get().toString().equalsIgnoreCase("desc")) {
//                sortDirection = Sort.Direction.DESC;
//            }
//            Sort sort = Sort.by(sortDirection, sortByValue);
//            carServices = carServiceRepository.findAll(sort);
//        }
//
//        return carServices;
//    }
}
