package com.example.smartgarage.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

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
