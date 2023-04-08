package com.example.smartgarage.models.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Entity
@Table(name = "vehicles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicle_id;


    @NotNull
    @Length(min = 17, max = 17)
    @Column(name = "VIN", length = 17)
    private String VIN;

    @NotNull
    @Column(name = "license_plate", length = 8)
    private String license_plate;


    @NotNull
    @Length(min = 2, max = 32)
    @Column(name = "model")
    private String model;

    @NotNull
    @Length(min = 2, max = 32)
    @Column(name = "brand")
    private String brand;

    @NotNull
    @Positive
    @Min(1886)
    @Column(name = "creation_year", length = 4)
    private Long creation_year;


    @Override
    public String toString() {
        return "Vehicle{" + vehicle_id +
                "VIN=" + VIN +
                ", license_plate='" + license_plate + '\'' +
                ", model=" + model + '\'' +
                ", brand=" + brand + '\'' +
                ", creation_year=" + creation_year +
                '}';
    }
}


