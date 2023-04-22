package com.example.smartgarage.models.dtos;

import com.example.smartgarage.models.entities.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarModelDto {

    @NotNull
    @Length(min = 2, max = 32)
    private String modelName;

    private Brand brand;
}
