package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.CarService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CarServiceRepository extends JpaRepository<CarService, Long> {

    @Modifying
    @Query("delete from CarService c where c.id = :id")
    void deleteById(Long id);

    List<CarService> findByNameContainingIgnoreCase(String name);
}
