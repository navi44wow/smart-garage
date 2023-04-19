package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.dtos.CarServiceDto;
import com.example.smartgarage.models.entities.CarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CarServizService {

    public CarService update(CarService carService, CarServiceDto carServiceDto);

    public CarService getById(Long id);

    List<CarService> getAll();

    Optional<CarService> findById(Long id);

    void delete(Long id);

    void save(CarService car);



    <T> List<CarService> getAllGeneric(Optional<T> name, Optional<T> sortBy, Optional<T> sortOrder);

    List<CarService> getAll(Optional<String> name,
                            Optional<Integer> priceMinimum,
                            Optional<Integer> priceMaximum,
                            Optional<String> sortBy,
                            Optional<String> sortOrder);

    List<CarService> getAll(String name, int priceMinimum, int priceMaximum, String sortBy, String sortOrder);

}
