package com.example.smartgarage.services.mappers;

import com.example.smartgarage.models.dtos.VisitDto;
import com.example.smartgarage.models.entities.*;
import com.example.smartgarage.services.contracts.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VisitMapper {

    private final VisitService visitService;
    private final VehicleService vehicleService;

    private final ListOfServicesService listOfServicez;

    private final UserService userService;

    private final CarServizService carServizService;

    private final ModelMapper modelMapper;


    @Autowired
    public VisitMapper(VisitService visitService,
                       VehicleService vehicleService,
                       ListOfServicesService listOfServicez,
                       UserService userService,
                       CarServizService carServizService,
                       ModelMapper modelMapper) {
        this.visitService = visitService;
        this.vehicleService = vehicleService;
        this.listOfServicez = listOfServicez;
        this.userService = userService;
        this.carServizService = carServizService;
        this.modelMapper = modelMapper;
    }

    public Visit toObject(VisitDto visitDto) {
        Visit visit = new Visit();
        Vehicle vehicle = vehicleService.getById(visitDto.getVehicleId());
        LocalDate start = LocalDate.now();
        LocalDate due = start.plusDays(3);
        VisitStatus status = new VisitStatus(1L, "Not started");
        visit.setVehicle(vehicle);
        visit.setStartDate(start);
        visit.setDueDate(due);
        visit.setStatus(status);
        return visit;
    }

    public void addServices(Long id, VisitDto visit, Set<Long> ids) {
        Set<CarService> services = visit.getServiceIds()
                .stream()
                .map(carServizService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        ListOfServices listOfServices = new ListOfServices();
        for (CarService service : services) {
            listOfServices.setVisitID(id);
            listOfServices.setServiceID(service.getId());
            listOfServices.setServiceName(service.getName());
            listOfServices.setServicePrice(service.getPrice());
            listOfServicez.save(listOfServices);
        }
    }
}
