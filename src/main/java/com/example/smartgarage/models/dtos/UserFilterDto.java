package com.example.smartgarage.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFilterDto {
    private String username;

    private String email;

    private String phoneNumber;

    private String vehicleVin;

    private String vehicleModel;

    private String vehicleBrand;

    private String visitFirstDate;

    private String visitLastDate;

    private LocalDate visitDate;

    private String sortBy;

    private String sortOrder;


}
