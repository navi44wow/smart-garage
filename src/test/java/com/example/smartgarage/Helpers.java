package com.example.smartgarage;

import com.example.smartgarage.models.dtos.CarServiceDto;
import com.example.smartgarage.models.entities.*;
import com.example.smartgarage.models.enums.UserRole;
import com.example.smartgarage.models.filter_options.UserFilterOptions;
import com.example.smartgarage.repositories.UserRoleRepository;
import com.example.smartgarage.services.contracts.CarServizService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Helpers {

    private static UserRoleRepository userRoleRepository;

    public static User createMockAdmin() {
        User mockUser = createMockUser();
        UserRoleEntity employeeRole = userRoleRepository.findByRole(UserRole.EMPLOYEE).orElseThrow();

        mockUser.setRoles(List.of());
        return mockUser;
    }

    public static User createMockUser() {
        var mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUsername("MockUsername");
        mockUser.setPassword("MockPassword");
        mockUser.setLastName("MockLastName");
        mockUser.setFirstName("MockFirstName");
        mockUser.setPhoneNumber("0894053050");
        mockUser.setEmail("mock@user.com");
        return mockUser;
    }

    public static UserFilterOptions createMockFilterOptions() {
        return new UserFilterOptions(
                "name",
                "mock@abv.bg",
                "0000000000",
                "vehicleVin",
                "sort",
                "order",
                LocalDate.parse("2023-03-09"),
                LocalDate.parse("2023-03-02"),
                LocalDate.parse("2023-04-06"),
                "Username",
                "Asc");
    }

    public static CarService createMockCarService() {
        CarService service = new CarService();
        service.setId(1L);
        service.setName("MockService");
        service.setPrice(100);
        return service;

    }

    public static CarService createAnotherMockCarService() {
        CarService service = new CarService();
        service.setId(2L);
        service.setName("AnotherMockService");
        service.setPrice(200);
        return service;
    }

    public static Vehicle createMockVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(1L);
        vehicle.setUser(createMockUser());
        vehicle.setVIN("dlaksjdlak9089809");
        vehicle.setCreationYear(2023L);
        vehicle.setLicensePlate("CB1001AA");
        vehicle.setCarModelId(createMockModel());
        return vehicle;
    }

    public static Brand createMockBrand() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setBrandName("Tesla");
        return brand;
    }

    public static CarModel createMockModel() {
        CarModel carModel = new CarModel();
        carModel.setModelId(1L);
        carModel.setModelName("Y");
        carModel.setBrand(createMockBrand());
        return carModel;
    }

    public static VisitStatus createMockStatus() {
        VisitStatus visitStatus = new VisitStatus();
        visitStatus.setId(1L);
        visitStatus.setName("Not started");
        return visitStatus;
    }

    public static Visit createMockVisit() {
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setVehicle(createMockVehicle());
        visit.setStatus(createMockStatus());
        visit.setStartDate(LocalDate.of(2023, 4, 9));
        visit.setDueDate(LocalDate.of(2023, 5, 1));
        visit.setArchived(false);
        return visit;
    }
}
