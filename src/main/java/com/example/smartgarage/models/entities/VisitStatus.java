package com.example.smartgarage.models.entities;

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

    /*
    id 1 - not started.
    id 2 - in progress.
    id 3 - ready for pick_up.
     */

}
