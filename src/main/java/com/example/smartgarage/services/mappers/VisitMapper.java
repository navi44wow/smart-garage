package com.example.smartgarage.services.mappers;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.dtos.VisitDto;
import com.example.smartgarage.models.entities.*;
import com.example.smartgarage.models.enums.StatusCode;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.contracts.CarServizService;
import com.example.smartgarage.services.contracts.UserService;
import com.example.smartgarage.services.contracts.VehicleService;
import com.example.smartgarage.services.contracts.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VisitMapper {

    private final VisitService visitService;
    private final VehicleService vehicleService;

    private final UserService userService;

    private final CarServizService carServizService;

    private final ModelMapper modelMapper;


    @Autowired
    public VisitMapper(VisitService visitService, VehicleService vehicleService, UserService userService, CarServizService carServizService, ModelMapper modelMapper) {
        this.visitService = visitService;
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.carServizService = carServizService;
        this.modelMapper = modelMapper;
    }

    public Visit toObject(VisitDto visitDto) {
        Visit visit = new Visit();
        Vehicle vehicle = vehicleService.getById(visitDto.getVehicleId());
        Set<ListOfServices> services = visitDto.getServices();
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(3);
        VisitStatus status = new VisitStatus(1L, "Not started");
        visit.setVehicle(vehicle);
        visit.setStartDate(start);
        visit.setEndDate(end);
        visit.setStatus(status);
        //visit.setServices(services);
        return visit;
    }
}
