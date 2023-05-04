package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.Visit;
import com.example.smartgarage.models.entities.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    @Query("select vs from VisitStatus vs order by vs.id")
    List<VisitStatus> findAllStatuses();
}
