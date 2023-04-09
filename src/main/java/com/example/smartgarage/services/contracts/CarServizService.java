package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.dtos.CarServiceDto;
import com.example.smartgarage.models.entities.CarService;

public interface CarServizService {

    public CarService update(CarService carService, CarServiceDto carServiceDto);

    public CarService getById(Long id);

}
