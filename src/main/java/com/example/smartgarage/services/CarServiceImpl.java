package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.dtos.CarServiceDto;
import com.example.smartgarage.models.entities.CarService;
import com.example.smartgarage.repositories.CarServiceRepository;
import com.example.smartgarage.services.contracts.CarServizService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarServizService {

    private final SessionFactory sessionFactory;
    @Autowired
    private CarServiceRepository carServiceRepository;

    public CarServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<CarService> getAll() {
        return carServiceRepository.findAll();
    }

    public Optional<CarService> findById(Long id) {
        return carServiceRepository.findById(id);
    }

    public void save(CarService carService) {
        carServiceRepository.save(carService);
    }

    public void delete(Long id) {
        carServiceRepository.deleteById(id);
    }

    public CarService update(CarService carService, CarServiceDto carServiceDto) {
        carService.setName(carService.getName());
        carService.setPrice(carServiceDto.getPrice());
        carServiceRepository.save(carService);
        return carService;
    }

    public CarService getById(Long id) {
        return carServiceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id ", id.toString(), " was not found!"));
    }
}
