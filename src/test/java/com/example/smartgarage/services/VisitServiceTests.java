package com.example.smartgarage.services;

import com.example.smartgarage.repositories.VisitRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTests {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitServiceImpl visitService;



}
