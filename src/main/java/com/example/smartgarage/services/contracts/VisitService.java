package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.entities.Visit;
import com.example.smartgarage.models.entities.VisitStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface VisitService {
    List<Visit> getAll();

    Optional<Visit> getById(Long id);

    Visit getVisitById(Long id);

    void save(Visit visit);

    List<Visit> getAllByUsername(String username);

    List<VisitStatus> findAllStatuses();
}
