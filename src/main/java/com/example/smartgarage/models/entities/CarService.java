package com.example.smartgarage.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "services")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String username;
    @Column(name = "price")
    private double price;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Vehicle> vehicleId = new ArrayList<>();

    @Override
    public String toString() {
        return "CarService{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", price=" + price +
                '}';
    }
}