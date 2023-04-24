package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.dtos.CarModelDto;
import com.example.smartgarage.models.entities.CarModel;

import java.util.List;
import java.util.Optional;

public interface CarModelService {

    List<CarModel> getAll();

    CarModel getById(Long modelId);

    CarModel getByName(String modelName);

    List<CarModel> getByBrandName(String brandName);

    List<CarModel> getByBrandId(Long brandId);

    void save(CarModel carModel);

    public CarModel update(CarModel carModel, CarModelDto carModelDto);

    void deleteCarModelById(Long carModelId);

    int getModelsCount();

    Optional<CarModel> findByCarModelName(String carModelName);
}
