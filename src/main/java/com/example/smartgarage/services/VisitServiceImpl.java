package com.example.smartgarage.services;

import com.example.smartgarage.models.entities.Visit;
import com.example.smartgarage.repositories.VisitRepository;
import com.example.smartgarage.services.contracts.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl implements VisitService {

@Autowired
private VisitRepository repository;


    @Override
    public List<Visit> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Visit> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(Visit visit) {
        repository.save(visit);
    }

    @Override
    public List<Visit> getAllByUsername(String username) {
        List<Visit> visits = repository.findAll();
        return visits.stream()
                .filter(visit -> visit.getUser().getUsername().equals(username))
                .collect(Collectors.toList());
    }
}
