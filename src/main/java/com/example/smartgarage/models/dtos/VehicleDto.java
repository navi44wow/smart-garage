package com.example.smartgarage.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {


    @NotNull(message = "VIN cannot be empty")
    @Size(min = 17, max = 17, message = "VIN has to be exactly 17 chars. Numbers and letters only!")
    private String VIN;

    @NotNull(message = "license plate cannot be empty")
    @Size(min = 7, max = 8, message = "license plate has to be 8 symbols!")
    private String licensePlate;

    @NotNull(message = "creation year cannot be empty")
    @Positive
    @Min(1886)
    @Max(9999)

    private Long creationYear;

    private UUID userId;

    private Long carModelId;
}