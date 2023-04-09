package com.example.smartgarage.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarServiceDto {

    @NotBlank(message = "Service name cannot be empty")
    @Size(min = 2, max = 32)
    private String name;

    @Positive(message = "Price must be a positive number")
    @NotNull
    private int price;

}
