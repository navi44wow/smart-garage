package com.example.smartgarage.models.dtos;


import com.example.smartgarage.models.entities.User;
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
    private User user;
    private Long creationYear;
    private String sortBy;
    private String sortOrder;
}
