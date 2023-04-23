package com.example.smartgarage.models.filter_options;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
public class VisitFilterOptions {

    private Optional<String> username;
    private Optional<String> status;
    private Optional<LocalDate> visitFirstDate;
    private Optional<LocalDate> visitLastDate;
    private Optional<LocalDate> visitDate;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public VisitFilterOptions(String username,
                              String status,
                              LocalDate visitFirstDate,
                              LocalDate visitLastDate,
                              LocalDate visitDate,
                              String sortBy,
                              String sortOrder) {

        this.username = Optional.ofNullable(username);
        this.status = Optional.ofNullable(status);
        this.visitFirstDate = Optional.ofNullable(visitFirstDate);
        this.visitLastDate = Optional.ofNullable(visitLastDate);
        this.visitDate = Optional.ofNullable(visitDate);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);

    }
}
