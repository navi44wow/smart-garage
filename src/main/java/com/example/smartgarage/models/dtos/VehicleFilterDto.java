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

    private String brandName;
    private String carModelName;
    private UUID userId;

    private String username;
    private Long creationYear;
    private String sortBy;
    private String sortOrder;
}
