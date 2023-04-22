package com.example.smartgarage.models.dtos;

import com.example.smartgarage.models.entities.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarModelFilterDto {

    private String modelName;

    private Brand brand;

    private String sortBy;

    private String sortOrder;
}
