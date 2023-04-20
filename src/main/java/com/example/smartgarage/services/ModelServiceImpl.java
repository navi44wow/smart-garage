package com.example.smartgarage.services;

import com.example.smartgarage.models.dtos.ModelDto;

import com.example.smartgarage.models.entities.CarModel;
import com.example.smartgarage.repositories.BrandRepository;
import com.example.smartgarage.repositories.ModelRepository;
import com.example.smartgarage.services.contracts.ModelService;

import java.util.List;
import java.util.Optional;

import com.example.smartgarage.exceptions.EntityNotFoundException;

import org.hibernate.SessionFactory;

import org.springframework.stereotype.Service;


@Service
public class ModelServiceImpl implements ModelService {

    private final SessionFactory sessionFactory;

    private final ModelRepository modelRepository;

    private final BrandRepository brandRepository;

    public ModelServiceImpl(SessionFactory sessionFactory, ModelRepository modelRepository, BrandRepository brandRepository) {
        this.sessionFactory = sessionFactory;
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public List<CarModel> getAll() {
        return modelRepository.findAll();
    }

    @Override
    public CarModel getById(Long modelId) {
        return modelRepository.findById(modelId).orElseThrow(() ->
                new EntityNotFoundException("Model with id ", modelId.toString(), " was not found!"));
    }

    @Override
    public CarModel getByName(String modelName) {
        return modelRepository.findByModelName(modelName).orElseThrow(() ->
                new EntityNotFoundException("Model with name ", modelName, " was not found!"));
    }

    @Override
    public List<CarModel> getByBrandName(String brandName) {
        return modelRepository.searchAllByBrand_BrandName(brandName);
    }

    @Override
    public List<CarModel> getByBrandId(Long brandId) {
        return modelRepository.findAllByBrandId(brandId);
    }

    @Override
    public void save(CarModel carModel) {
        modelRepository.save(carModel);

    }

    @Override
    public CarModel update(CarModel carModel, ModelDto modelDto) {
        carModel.setModelName(modelDto.getModelName());
        carModel.setBrand(modelDto.getBrand());
        modelRepository.save(carModel);
        return carModel;
    }

    @Override
    public void deleteModelById(Long modelId) {
        modelRepository.deleteById(modelId);
    }

    @Override
    public int getModelsCount() {
        return 0;
    }

    @Override
    public Optional<CarModel> findByModelName(String modelName) {
        return modelRepository.findByModelName(modelName);
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
