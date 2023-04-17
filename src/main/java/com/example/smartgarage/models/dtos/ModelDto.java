package com.example.smartgarage.models.dtos;

import com.example.smartgarage.models.entities.Brand;
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
public class ModelDto {

    @Column(name = "model_id")
    private Long modelId;

    @NotNull
    @Length(min = 2, max = 32)
    @Column(name = "model_name")
    private String modelName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brandId;
}
