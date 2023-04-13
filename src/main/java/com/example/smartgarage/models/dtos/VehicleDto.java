package com.example.smartgarage.models.dtos;


import com.example.smartgarage.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @Size(min = 7, max = 8, message = "license plate has to be 8 symbols!")
    private String licensePlate;

    @NotNull(message = "creation year cannot be empty")
    @Positive
    @Min(1886)
    private Long creationYear;

    private User user;

}