package com.example.smartgarage.services;

import com.example.smartgarage.models.dtos.VisitFilterDto;
import com.example.smartgarage.models.entities.Visit;
import com.example.smartgarage.models.entities.VisitStatus;
import com.example.smartgarage.repositories.UserRepository;
import com.example.smartgarage.repositories.VisitRepository;
import com.example.smartgarage.services.contracts.VisitService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<Visit> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Visit> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Visit getVisitById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visit with id " + id + " not found"));
    }

    @Override
    public void save(Visit visit) {
        repository.save(visit);
    }

    @Override
    public List<Visit> getAllByUsername(String username) {
        List<Visit> visits = repository.findAll();
        return visits.stream()
                .filter(visit -> visit.getVehicle().getUser().getUsername().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitStatus> findAllStatuses() {
        try (Session session = sessionFactory.openSession()) {
            Query<VisitStatus> request = session.createNativeQuery("select * from visit_status order by id ", VisitStatus.class);
            return request.list();
        }
    }

    @Override
    public List<Visit> getAllSorted(String sortBy, String sortOrder) {
        List<Visit> allVisits = repository.findAll();
        if (sortBy != null && !sortBy.equals("none") && sortOrder != null && (sortOrder.equals("asc") || sortOrder.equals("desc"))) {
            Comparator<Visit> comparator = null;
            switch (sortBy) {
                case "username":
                    comparator = Comparator.comparing(v -> v.getVehicle().getUser().getUsername());
                    break;
                case "status":
                    comparator = Comparator.comparing(v -> v.getStatus().getName());
                    break;
                case "createDate":
                    comparator = Comparator.comparing(Visit::getStartDate);
                    break;
                case "dueDate":
                    comparator = Comparator.comparing(Visit::getDueDate);
                    break;
            }
            if (comparator != null) {
                if (sortOrder.equals("asc")) {
                    allVisits.sort(comparator);
                } else {
                    allVisits.sort(comparator.reversed());
                }
            }
        }
        return allVisits;
    }
}