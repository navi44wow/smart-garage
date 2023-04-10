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
    private Long vehicleId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;


    @NotNull
    @Length(min = 17, max = 17)
    @Column(name = "VIN", length = 17)
    private String VIN;

    @NotNull
    @Column(name = "license_plate", length = 8)
    private String license_plate;


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
    private Long creation_year;


    @Override
    public String toString() {
        return "Vehicle{" + vehicleId +
                "VIN=" + VIN +
                ", license_plate='" + license_plate + '\'' +
                ", model=" + model + '\'' +
                ", brand=" + brand + '\'' +
                ", creation_year=" + creation_year +
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

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(@NotNull String license_plate) {
        this.license_plate = license_plate;
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

    public Long getCreation_year() {
        return creation_year;
    }

    public void setCreation_year(@NotNull Long creation_year) {
        this.creation_year = creation_year;
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