package com.example.smartgarage.services;

import com.example.smartgarage.models.dtos.ModelDto;
import com.example.smartgarage.models.entities.Model;
import com.example.smartgarage.repositories.BrandRepository;
import com.example.smartgarage.repositories.ModelRepository;
import com.example.smartgarage.services.contracts.ModelService;

import java.util.HashMap;
import java.util.List;


import com.example.smartgarage.exceptions.EntityNotFoundException;

import com.example.smartgarage.services.queries.BrandModelQueryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;
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
    public List<Model> getAll() {
        return modelRepository.findAll();
    }

    @Override
    public Model getById(Long modelId) {
        return modelRepository.findById(modelId).orElseThrow(() ->
                new EntityNotFoundException("Model with id ", modelId.toString(), " was not found!"));
    }

    @Override
    public Model getByName(String modelName) {
        return modelRepository.findByModelName(modelName).orElseThrow(() ->
                new EntityNotFoundException("Model with name ", modelName, " was not found!"));
    }

    @Override
    public void save(Model model) {
        modelRepository.save(model);
    }

    @Override
    public Model update(Model model, ModelDto modelDto) {
        model.setModelName(modelDto.getModelName());
        model.setBrandId(modelDto.getBrandId());
        modelRepository.save(model);
        return model;
    }

    @Override
    public void deleteModelById(Long modelId) {
        modelRepository.deleteById(modelId);
    }

    @Override
    public int getModelsCount() {
        return 0;
    }
}