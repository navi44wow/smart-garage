package com.example.smartgarage.models.entities;

import com.example.smartgarage.models.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "visit_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VisitStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public static VisitStatus valueOf(String name) {
        for (StatusCode statusCode : StatusCode.values()) {
            if (statusCode.getStatus().getName().equalsIgnoreCase(name)) {
                return statusCode.getStatus();
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + name + "]");
    }

    @Override
    public String toString() {
        return name;
    }
}
