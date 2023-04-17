package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.dtos.ModelDto;
import com.example.smartgarage.models.entities.Model;

import java.util.List;

public interface ModelService {

    List<Model> getAll();

    Model getById(Long modelId);

    Model getByName(String modelName);

    void save(Model model);

    public Model update(Model model, ModelDto modelDto);

    void deleteModelById(Long modelId);

    int getModelsCount();


}
