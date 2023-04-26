package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.dtos.VisitFilterDto;
import com.example.smartgarage.models.entities.CarService;
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

    List<Visit> getAllVisits(Boolean onlyArchived, Boolean includeArchived, VisitFilterDto visitFilterDto);

    Visit toggleArchivedStatus(Visit visit);

    List<CarService> getFilteredCarServices(Visit visit);
}
