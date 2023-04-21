package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.dtos.ModelDto;
import com.example.smartgarage.models.entities.Model;

import java.util.List;
import java.util.Optional;

public interface ModelService {

    List<Model> getAll();

    Model getById(Long modelId);

    Model getByName(String modelName);

    List<Model> getByBrandName(String brandName);

    List<Model> getByBrandId(Long brandId);

    void save(Model model);

    public Model update(Model model, ModelDto modelDto);

    void deleteModelById(Long modelId);

    int getModelsCount();

    Optional<Model> findByModelName(String modelName);
}
