package com.example.smartgarage.models.dtos;


import com.example.smartgarage.models.entities.Model;
import com.example.smartgarage.models.entities.User;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {

    @Column(name = "vehicle_id")
    private Long vehicleId;


    @NotNull(message = "VIN cannot be empty")
    @Size(min = 17, max = 17, message = "VIN has to be exactly 17 chars. Numbers and letters only!")
    private String VIN;

    @NotNull(message = "license plate cannot be empty")
    @Size(min = 7, max = 8, message = "license plate has to be 8 symbols!")
    private String licensePlate;

    @NotNull(message = "creation year cannot be empty")
    @Positive
    @Min(1886)
    @Max(9999)

    private Long creationYear;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView
    @JoinColumn(name = "user_id")
    private User userId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id")
    private Model modelId;
}