package com.example.smartgarage.models.entities;

import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Objects;


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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    @NotNull
    @Length(min = 17, max = 17)
    @Column(name = "VIN", length = 17)
    private String VIN;

    @NotNull
    @Column(name = "license_plate")
    @Size(min = 7, max = 8, message = "license plate has to be between 7 and 8 symbols!")
    private String licensePlate;


    @NotNull
    @Length(min = 2, max = 32)
    @Column(name = "model")
    private String model;

    @NotNull
    @Length(min = 2, max = 32)
    @Column(name = "brand")
    private String brand;

    @NotNull
    @Positive
    @Min(1886)
    @Column(name = "creation_year", length = 4)
    private Long creationYear;


    @Override
    public String toString() {
        return "Vehicle{" + vehicleId +
                "VIN: " + VIN +
                ", license plate: '" + licensePlate + '\'' +
                ", model: " + model + '\'' +
                ", brand: " + brand + '\'' +
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