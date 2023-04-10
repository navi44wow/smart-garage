package com.example.smartgarage.models.dtos;


import com.example.smartgarage.models.entities.User;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class VehicleDto {


    @NotNull(message = "VIN cannot be empty")
    @Size(min = 17, max = 17, message = "VIN has to be exactly 17 chars. Numbers and letters only!")
    private String VIN;

    @NotNull(message = "brand cannot be empty")
    @Size(min = 2, max = 32, message = "Brand has to be between 2 and 32 symbols")
    private String brand;

    @NotNull(message = "model cannot be empty")
    @Size(min = 2, max = 32, message = "Model has to be between 2 and 32 symbols")
    private String model;

    @NotNull(message = "license plate cannot be empty")
    @Size(min = 8, max = 8, message = "license plate has to be 8 symbols!")
    private String license_plate;

    @NotNull(message = "creation year plate cannot be empty")
    @Positive
    @Min(1886)
    private Long creation_year;

    private User user;


    public VehicleDto() {
    }


    @NonNull
    public User getUser() {
        return user;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
    }


    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public Long getCreation_year() {
        return creation_year;
    }

    public void setCreation_year(Long creation_year) {
        this.creation_year = creation_year;
    }


}


