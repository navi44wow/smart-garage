package com.example.smartgarage.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "visits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private Long id;

   // @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private VisitStatus status;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "due_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "visit_id")
    private Set<ListOfServices> services;

    public int displaySum() {
        int sum = services.stream()
                .mapToInt(ListOfServices::getServicePrice)
                .sum();
        return sum;
    }

    public String displayServices() {
        List<String> servicesList = this.getServices()
                .stream()
                .map(service -> service.getServiceName() + ": " + service.getServicePrice() + " BGN.")
                .collect(Collectors.toList());

        String servicesString = String.join("\n", servicesList);
        servicesString = servicesString.replaceAll(",", "");
        servicesString = servicesString.replaceAll("\\[", "");
        servicesString = servicesString.replaceAll("\\]", "");

        return servicesString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return id == visit.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Column(name = "is_archived")
    private boolean isArchived;


    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", vehicle=" + vehicle +
                ", status=" + status.toString() +
                ", startDate=" + startDate.toString() +
                ", endDate=" + dueDate.toString() +
                '}';
    }
}
