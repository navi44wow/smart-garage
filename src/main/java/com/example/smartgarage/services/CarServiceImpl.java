package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.CarServiceDto;
import com.example.smartgarage.models.entities.CarService;
import com.example.smartgarage.repositories.CarServiceRepository;
import com.example.smartgarage.services.contracts.CarServizService;
import com.example.smartgarage.services.queries.ServicesQueryMaker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarServizService {

    @Autowired
    private CarServiceRepository carServiceRepository;

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    private AuthenticationHelper authenticationHelper;


    public List<CarService> getAll() {
        return carServiceRepository.findAll();
    }

    public Optional<CarService> findById(Long id) {
        return carServiceRepository.findById(id);
    }

    public void save(CarService carService) {
        carServiceRepository.save(carService);
    }

    @Override
    public List<CarService> getAll(String name, int priceMinimum, int priceMaximum, String sortBy, String sortOrder) {
        List<CarService> carServices;
        if (name != null && !name.isBlank()) {
            carServices = carServiceRepository.findByNameContainingIgnoreCase(name);
        } else {
            carServices = carServiceRepository.findAll();
        }

        if (sortBy != null && !sortBy.isBlank()) {
            Sort.Direction sortDirection = Sort.Direction.ASC;
            if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
            Sort sort = Sort.by(sortDirection, sortBy);
            carServices = carServiceRepository.findAll(sort);
        }

        return carServices;
    }

    @Override
    public <T> List<CarService> getAllGeneric(Optional<T> name, Optional<T> sortBy, Optional<T> sortOrder) {
        List<CarService> carServices;
        if (name.isPresent() && !name.get().toString().isBlank()) {
            carServices = carServiceRepository.findByNameContainingIgnoreCase(name.get().toString());
        } else {
            carServices = carServiceRepository.findAll();
        }

        if (sortBy.isPresent() && !sortBy.get().toString().isBlank()) {
            String sortByValue = sortBy.get().toString();
            Sort.Direction sortDirection = Sort.Direction.ASC;
            if (sortOrder.isPresent() && sortOrder.get().toString().equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
            Sort sort = Sort.by(sortDirection, sortByValue);
            carServices = carServiceRepository.findAll(sort);
        }

        return carServices;
    }


    //For Rest Api
    @Override
    public List<CarService> getAll(Optional<String> name,
                                   Optional<Integer> priceMinimum,
                                   Optional<Integer> priceMaximum,
                                   Optional<String> sortBy,
                                   Optional<String> sortOrder) {

        try (Session session = sessionFactory.openSession()) {

                ServicesQueryMaker queryMaker = new ServicesQueryMaker();
                String query = queryMaker.buildHQLSearchAndSortQuery(name, priceMinimum, priceMaximum, sortBy, sortOrder);
                HashMap<String, Object> propertiesMap = queryMaker.getProperties();

                Query<CarService> request = session.createQuery(query, CarService.class);
                request.setProperties(propertiesMap);

                return request.list();
            }
        }


    public void delete(Long id) {
        carServiceRepository.deleteById(id);
    }

    public CarService update(CarService carService, CarServiceDto carServiceDto) {
        carService.setName(carServiceDto.getName());
        carService.setPrice(carServiceDto.getPrice());
        carServiceRepository.save(carService);
        return carService;
    }

    public CarService getById(Long id) {
        return carServiceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id ", id.toString(), " was not found!"));
    }
}
