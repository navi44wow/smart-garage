package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.dtos.VisitFilterDto;
import com.example.smartgarage.models.entities.CarService;
import com.example.smartgarage.models.entities.ListOfServices;
import com.example.smartgarage.models.entities.Visit;
import com.example.smartgarage.models.entities.VisitStatus;
import com.example.smartgarage.repositories.UserRepository;
import com.example.smartgarage.repositories.VisitRepository;
import com.example.smartgarage.services.contracts.CarServizService;
import com.example.smartgarage.services.contracts.VisitService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitRepository repository;

    @Autowired
    private CarServizService carServizService;

    @Autowired
    UserRepository userRepository;

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
                .orElseThrow(() -> new EntityNotFoundException("Visit", id));
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

    public Visit toggleArchivedStatus(Visit visit) {
        if (visit.isArchived()) {
            visit.setArchived(false);
        } else {
            visit.setArchived(true);
        }
        return visit;
    }

    @Override
    public List<CarService> getFilteredCarServices(Visit visit) {
        Set<ListOfServices> listOfServices = visit.getServices();
        List<CarService> services = carServizService.getAll();
        List<CarService> filteredServices = new ArrayList<>();
        for (CarService service : services) {
            boolean isPresent = false;
            for (ListOfServices listOfService : listOfServices) {
                if (listOfService.getServiceID().equals(service.getId())) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                filteredServices.add(service);
            }
        }
        return filteredServices;
    }

    /**
     * Retrieves all visits based on the given filters and sorting criteria.
     *
     * @param onlyArchived   {@code true} to include only archived visits, {@code false} to include only non-archived visits, or {@code null} to include both archived and non-archived visits.
     * @param includeArchived   {@code true} to include both archived and non-archived visits, {@code false} to include only non-archived visits, or {@code null} to include only archived visits.
     * @param visitFilterDto   the filters to apply to the visits.
     * @return a list of visits that match the given filters and sorting criteria.
     */
    @Override
    public List<Visit> getAllVisits(Boolean onlyArchived, Boolean includeArchived, VisitFilterDto visitFilterDto) {
        List<Visit> allVisits = getAllSorted(visitFilterDto.getSortBy(), visitFilterDto.getSortOrder());
        if (onlyArchived != null && onlyArchived) {
            allVisits = filterArchivedVisits(allVisits);
        } else if (includeArchived != null && includeArchived) {
            allVisits = includeArchivedVisits(allVisits);
        } else {
            allVisits = filterNonArchivedVisits(allVisits);
        }
        if (visitFilterDto.getUsername() != null && !visitFilterDto.getUsername().isEmpty()) {
            allVisits = filterVisitsByUser(allVisits, visitFilterDto.getUsername());
        }
        if (visitFilterDto.getBrand() != null && !visitFilterDto.getBrand().isEmpty()) {
            allVisits = filterVisitsByBrand(allVisits, visitFilterDto.getBrand());
        }
        if (visitFilterDto.getStatus() != null && !visitFilterDto.getStatus().isEmpty()) {
            allVisits = filterVisitsByStatus(allVisits, visitFilterDto.getStatus());
        }
        if (visitFilterDto.getStartDate() != null && visitFilterDto.getEndDate() != null) {
            LocalDate startDate = visitFilterDto.getStartDate();
            LocalDate endDate = visitFilterDto.getEndDate();
            allVisits = filterVisitsByDateRange(allVisits, startDate, endDate);
        } else if (visitFilterDto.getStartDate() != null) {
            LocalDate startDate = visitFilterDto.getStartDate();
            allVisits = filterVisitsByStartDate(allVisits, startDate);
        }
        return allVisits;
    }

    private List<Visit> filterArchivedVisits(List<Visit> visits) {
        return visits.stream().filter(Visit::isArchived).collect(Collectors.toList());
    }

    private List<Visit> includeArchivedVisits(List<Visit> visits) {
        return new ArrayList<>(visits);
    }

    private List<Visit> filterNonArchivedVisits(List<Visit> visits) {
        return visits.stream().filter(visit -> !visit.isArchived()).collect(Collectors.toList());
    }

    private List<Visit> filterVisitsByUser(List<Visit> visits, String username) {
        return visits.stream().filter(visit -> visit.getVehicle().getUser().getUsername().
                equalsIgnoreCase(username)).collect(Collectors.toList());
    }

    private List<Visit> filterVisitsByBrand(List<Visit> visits, String brand) {
        return visits.stream().filter(visit -> visit.getVehicle().getCarModelId().getBrand().getBrandName().
                equalsIgnoreCase(brand)).collect(Collectors.toList());
    }

    private List<Visit> filterVisitsByStatus(List<Visit> visits, String status) {
        return visits.stream().filter(visit -> visit.getStatus().getName().
                equals(status)).collect(Collectors.toList());
    }

    private List<Visit> filterVisitsByDateRange(List<Visit> visits, LocalDate startDate, LocalDate endDate) {
        return visits.stream().filter(visit -> visit.getStartDate().isAfter(startDate.minusDays(1)) &&
                visit.getStartDate().isBefore(endDate.plusDays(1))).collect(Collectors.toList());
    }

    private List<Visit> filterVisitsByStartDate(List<Visit> visits, LocalDate startDate) {
        return visits.stream().filter(visit -> visit.getStartDate().isEqual(startDate)).collect(Collectors.toList());
    }

    private List<Visit> getAllSorted(String sortBy, String sortOrder) {
        List<Visit> allVisits = repository.findAll();
        if (sortBy != null && !sortBy.equals("none") && sortOrder != null &&
                (sortOrder.equals("asc") || sortOrder.equals("desc"))) {
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