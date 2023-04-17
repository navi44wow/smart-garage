package com.example.smartgarage.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarServizFilterDto {

    private String name;
    private int priceMinimum;
    private int priceMaximum;
    private String sortBy;
    private String sortOrder;


}
