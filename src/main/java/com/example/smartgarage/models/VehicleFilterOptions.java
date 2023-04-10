package com.example.smartgarage.models;


import com.example.smartgarage.models.entities.User;

import java.util.Optional;

public class VehicleFilterOptions {
    private final Optional<Long> vehicleId;
    private final Optional<String> VIN;
    private final Optional<String> sortBy;
    private final Optional<String> sortOrder;
    private final Optional<User> user;
    private final Optional<Long> userId;

    private final Optional<String> brand;
    private final Optional<String> model;
    private final Optional<String> license_plate;


    public VehicleFilterOptions() {
        this(null, null, null, null, null, null, null, null, null);
    }


    public VehicleFilterOptions(
            Long vehicleId,
            String VIN,
            String sortBy,
            String sortOrder,
            User user,
            Long userId,
            String brand,
            String model,
            String license_plate) {

        this.vehicleId = Optional.ofNullable(vehicleId);
        this.VIN = Optional.ofNullable(VIN);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
        this.user = Optional.ofNullable(user);
        this.userId = Optional.ofNullable(userId);
        this.brand = Optional.ofNullable(brand);
        this.model = Optional.ofNullable(model);
        this.license_plate = Optional.ofNullable(license_plate);
    }

    public Optional<Long> getVehicle_id() {
        return vehicleId;
    }

    public Optional<String> getVIN() {
        return VIN;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }

    public Optional<User> getUser() {
        return user;
    }

    public Optional<Long> getUserId() {
        return userId;
    }

    public Optional<String> getBrand() {
        return brand;
    }

    public Optional<String> getModel() {
        return model;
    }

    public Optional<String> getLicense_plate() {
        return license_plate;
    }
}
