package com.example.smartgarage.models.entities;

import com.example.smartgarage.models.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "vehicles")
@Getter
@Setter
@AllArgsConstructor


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
    @Column(name = "license_plate", length = 8)
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

    public Vehicle() {
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(@NotNull Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public User getUserId() {
        return user;
    }

    public void setUserId(@NotNull User user) {
        this.user = user;
    }

    @NonNull
    public User getUser() {
        return user;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(@NotNull String VIN) {
        this.VIN = VIN;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(@NotNull String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(@NotNull String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(@NotNull String brand) {
        this.brand = brand;
    }

    public Long getCreationYear() {
        return creationYear;
    }

    public void setCreationYear(@NotNull Long creationYear) {
        this.creationYear = creationYear;
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