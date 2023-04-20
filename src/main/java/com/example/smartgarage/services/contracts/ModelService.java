package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.dtos.ModelDto;
import com.example.smartgarage.models.entities.CarModel;

import java.util.List;
import java.util.Optional;

public interface ModelService {

    List<CarModel> getAll();

    CarModel getById(Long modelId);

    CarModel getByName(String modelName);

    List<CarModel> getByBrandName(String brandName);

    List<CarModel> getByBrandId(Long brandId);

    void save(CarModel carModel);

    public CarModel update(CarModel carModel, ModelDto modelDto);

    void deleteModelById(Long modelId);

    int getModelsCount();

    Optional<CarModel> findByModelName(String modelName);
}
