package com.example.smartgarage.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "vehicles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;


    @NotNull
    @Length(min = 17, max = 17)
    @Column(name = "VIN", length = 17)
    private String VIN;

    @NotNull
    @Column(name = "license_plate")
    @Size(min = 7, max = 8, message = "license plate has to be between 7 and 8 symbols!")
    private String licensePlate;

    @NotNull
    @Positive
    @Min(1886)
    @Max(9999)
    @Column(name = "creation_year", length = 4)
    private Long creationYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id")
    private CarModel carModelId;

    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Visit> visits;


    @Override
    public String toString() {
        return "Vehicle{" + vehicleId +
                "VIN: " + VIN +
                ", user id: '" + user + '\'' +
                ", license plate: '" + licensePlate + '\'' +
                ", model: '" + carModelId + '\'' +
                ", creation year: " + creationYear +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return vehicleId == vehicle.vehicleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId);
    }
}