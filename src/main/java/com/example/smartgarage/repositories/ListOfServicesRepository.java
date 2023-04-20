package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.ListOfServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListOfServicesRepository extends JpaRepository<ListOfServices, Long> {
}
