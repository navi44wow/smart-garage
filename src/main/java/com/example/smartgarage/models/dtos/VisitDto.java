package com.example.smartgarage.models.dtos;

import com.example.smartgarage.models.entities.ListOfServices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class VisitDto {

    private Long vehicleId;
    private Long statusId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<ListOfServices> services;
}
