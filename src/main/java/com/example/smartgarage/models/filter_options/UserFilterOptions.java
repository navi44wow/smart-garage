package com.example.smartgarage.models.filter_options;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;


@Getter
@Setter
public class UserFilterOptions {

    private Optional<String> username;
    private Optional<String> email;
    private Optional<String> phoneNumber;
    private Optional<String> vehicleModel;
    private Optional<String> vehicleBrand;
    private Optional<LocalDate> visitFirstDate;
    private Optional<LocalDate> visitLastDate;
    private Optional<LocalDate> visitDate;

    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public UserFilterOptions(String username, String email, String phoneNumber,
                             String vehicleModel, String vehicleBrand, LocalDate visitFirstDate,
                             LocalDate visitLastDate, LocalDate visitDate, String sortBy, String sortOrder) {
        this.username = Optional.ofNullable(username);
        this.email = Optional.ofNullable(email);
        this.phoneNumber = Optional.ofNullable(phoneNumber);
        this.vehicleModel = Optional.ofNullable(vehicleModel);
        this.vehicleBrand = Optional.ofNullable(vehicleBrand);
        this.visitFirstDate = Optional.ofNullable(visitFirstDate);
        this.visitLastDate = Optional.ofNullable(visitLastDate);
        this.visitDate = Optional.ofNullable(visitDate);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }
}
