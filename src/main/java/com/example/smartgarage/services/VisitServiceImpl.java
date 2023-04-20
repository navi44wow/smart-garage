package com.example.smartgarage.services;

import com.example.smartgarage.models.entities.Visit;
import com.example.smartgarage.models.entities.VisitStatus;
import com.example.smartgarage.repositories.VisitRepository;
import com.example.smartgarage.services.contracts.VisitService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitRepository repository;

    @Autowired
    private SessionFactory sessionFactory;


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

//    public List<Visit> getAll(boolean includingArchived) {
//        if (includingArchived) {
//            return repository.findAll();
//        } else {
//            return repository.findByArchivedFalse();
//        }
//    }
}
