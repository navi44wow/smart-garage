package com.example.smartgarage.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {


    @Column(name = "brand_id")
    private Long brandId;


    @NotNull
    @Length(min = 2, max = 32)
    @Column(name = "brand_name")
    private String brandName;
}
