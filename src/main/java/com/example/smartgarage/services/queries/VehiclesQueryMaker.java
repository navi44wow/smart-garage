package com.example.smartgarage.services.queries;

import java.util.HashMap;
import java.util.Optional;

public class VehiclesQueryMaker {

    private StringBuilder queryBuilder = new StringBuilder();
    private HashMap<String, Object> propertiesMap = new HashMap<>();

    public String buildHQLSearchAndSortQueryVehicles(Optional<String> model, Optional<String> brand, Optional<Integer> creationYearMin,
                                                     Optional<Integer> creationYearMax, Optional<String> sortBy,
                                                     Optional<String> sortOrder) {

        queryBuilder.append("FROM Vehicle ");

        if (model.isPresent() || brand.isPresent() || creationYearMin.isPresent() || creationYearMax.isPresent()) {
            queryBuilder.append("WHERE ");

            if (model.isPresent()) {
                queryBuilder.append("model LIKE :model AND ");
                propertiesMap.put("model", "%" + model.get() + "%");
            }


            if (brand.isPresent()) {
                queryBuilder.append("brand LIKE :brand AND ");
                propertiesMap.put("brand", "%" + brand.get() + "%");
            }

            if (creationYearMin.isPresent()) {
                queryBuilder.append("creationYear >= :creationYearMin AND ");
                propertiesMap.put("creationYearMin", creationYearMin.get());
            }

            if (creationYearMax.isPresent()) {
                queryBuilder.append("creationYear <= :creationYearMax AND ");
                propertiesMap.put("creationYearMax", creationYearMax.get());
            }

            queryBuilder.setLength(queryBuilder.length() - 5);
        }

        if (sortBy.isPresent()) {
            queryBuilder.append("ORDER BY ");
            queryBuilder.append(sortBy.get());

            if (sortOrder.isPresent()) {
                queryBuilder.append(" ");
                queryBuilder.append(sortOrder.get());
            }
        }

        return queryBuilder.toString();
    }

    public HashMap<String, Object> getProperties() {
        return propertiesMap;
    }
}