package com.example.smartgarage.models.service_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarServiceModel {

    @Size(min = 5, max = 50)
    private String serviceName;

    @Positive
    @NotNull
    private int price;

}
