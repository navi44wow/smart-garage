package com.example.smartgarage.services.queries;

import java.util.HashMap;
import java.util.Optional;

public class ServicesQueryMaker {

    private StringBuilder queryBuilder = new StringBuilder();
    private HashMap<String, Object> propertiesMap = new HashMap<>();

    public String buildHQLSearchAndSortQuery(Optional<String> name, Optional<Integer> priceMinimum,
                                             Optional<Integer> priceMaximum, Optional<String> sortBy,
                                             Optional<String> sortOrder) {

        queryBuilder.append("FROM CarService ");

        if (name.isPresent() || priceMinimum.isPresent() || priceMaximum.isPresent()) {
            queryBuilder.append("WHERE ");

            if (name.isPresent()) {
                queryBuilder.append("name LIKE :name AND ");
                propertiesMap.put("name", "%" + name.get() + "%");
            }

            if (priceMinimum.isPresent()) {
                queryBuilder.append("price >= :priceMinimum AND ");
                propertiesMap.put("priceMinimum", priceMinimum.get());
            }

            if (priceMaximum.isPresent()) {
                queryBuilder.append("price <= :priceMaximum AND ");
                propertiesMap.put("priceMaximum", priceMaximum.get());
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
