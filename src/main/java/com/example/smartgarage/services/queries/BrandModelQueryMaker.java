package com.example.smartgarage.services.queries;

import com.example.smartgarage.models.entities.Brand;
import com.example.smartgarage.repositories.BrandRepository;

import java.util.HashMap;
import java.util.Optional;

public class BrandModelQueryMaker {


    private StringBuilder queryBuilder = new StringBuilder();
    private HashMap<String, Object> propertiesMap = new HashMap<>();

    private final BrandRepository brandRepository;

    public BrandModelQueryMaker(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    public String buildHQLSearchBrandModel(String brandName) {

        Long brandId = brandRepository.findByBrandName(brandName).get().getId();

        queryBuilder.append("FROM Model ");

        queryBuilder.append("WHERE ");
        queryBuilder.append("brand_id LIKE :brandId ");
        propertiesMap.put("brandId", "%" + brandId.getClass() + "%");

        return queryBuilder.toString();
    }

    public HashMap<String, Object> getProperties() {
        return propertiesMap;
    }


    public String buildHQLSearchByUserId(Optional<String> userId) {

        queryBuilder.append("FROM vehicles WHERE user_id =" + userId);
        return queryBuilder.toString();
    }
}

//
//        SELECT* FROM vehicles WHERE user_id = 'a4ad1d8c-b49d-4adb-8029-38bf5ef6e901';

