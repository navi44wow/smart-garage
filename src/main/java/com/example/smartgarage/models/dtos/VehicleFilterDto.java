package com.example.smartgarage.models.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleFilterDto {

    private String brand;
    private String model;
    private UUID user;
    private int creationYearMin;
    private int creationYearMax;
    private String sortBy;
    private String sortOrder;
}
