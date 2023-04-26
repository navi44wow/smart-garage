package com.example.smartgarage.models.filter_options;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Getter
@Setter
public class VisitFilterOptions {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

    private Optional<String> username;
    private Optional<String> status;
    private Optional<LocalDate> visitFirstDate;
    private Optional<LocalDate> visitLastDate;
    private Optional<LocalDate> visitDate;
    private Optional<String> vehicleId;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;
//    private Optional<String> searchBy;
//    private Optional<String> keyword;

    public VisitFilterOptions(String username,
                              String status,
                              String vehicleId,
                              LocalDate visitFirstDate,
                              LocalDate visitLastDate
                              ) {
        this.username = Optional.ofNullable(username);
        this.status = Optional.ofNullable(status);
        this.vehicleId = Optional.ofNullable(vehicleId);
        this.visitFirstDate = Optional.ofNullable(visitFirstDate);
        this.visitLastDate = Optional.ofNullable(visitLastDate);
    }

    public VisitFilterOptions(String username,
                              String status,
                              String vehicleId,
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
        this.vehicleId = Optional.ofNullable(vehicleId);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);

        //String formattedDate = visitFirstDate.format (formatter::format).orElse(null);
//        this.searchBy = Optional.ofNullable(searchBy);
//        this.keyword = Optional.ofNullable(keyword);




    }
}
