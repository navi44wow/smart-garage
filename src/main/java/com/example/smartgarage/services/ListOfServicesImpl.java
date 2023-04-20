package com.example.smartgarage.services;

import com.example.smartgarage.models.entities.ListOfServices;
import com.example.smartgarage.repositories.ListOfServicesRepository;
import com.example.smartgarage.services.contracts.ListOfServicesService;
import org.springframework.stereotype.Service;

@Service
public class ListOfServicesImpl implements ListOfServicesService {

    private final ListOfServicesRepository repository;

    public ListOfServicesImpl(ListOfServicesRepository repository) {
        this.repository = repository;
    }

    public void save(ListOfServices listOfServices){
        repository.save(listOfServices);
    }
}
