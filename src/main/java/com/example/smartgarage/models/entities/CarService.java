package com.example.smartgarage.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @Column(name = "service_id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;

    @Override
    public String toString() {
        return name + ": " +
                price+" BGN";
    }
}