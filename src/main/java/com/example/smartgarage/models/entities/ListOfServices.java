package com.example.smartgarage.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "list_of_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListOfServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;
    @Column(name = "visit_id")
    private Long visitID;
    @Column(name = "service_id")
    private Long serviceID;
    @Column(name = "service_name")
    String serviceName;

    @Column(name = "service_price")
    int servicePrice;

    public int getServicePrice() {
        return this.servicePrice;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListOfServices that = (ListOfServices) o;
        return visitID == that.visitID && serviceName.equals(that.serviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitID, serviceName);
    }
}
