package com.example.smartgarage.models.dtos;

import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Vehicle;
import com.example.smartgarage.models.entities.VisitStatus;
import com.example.smartgarage.models.view_models.UserViewModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class VisitDto {

    private Long id;
    private User user;
    private Vehicle vehicle;
   private VisitStatus status;
    private LocalDate startDate;
    private LocalDate endDate;

    @Override
    public String toString() {
        return "VisitDto{" +
                "user=" + user.toString() +
                ", vehicle=" + vehicle +
                ", visitStatus=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
