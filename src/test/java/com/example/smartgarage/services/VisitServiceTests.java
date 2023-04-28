package com.example.smartgarage.services;

import com.example.smartgarage.models.entities.Visit;
import com.example.smartgarage.repositories.VisitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.smartgarage.Helpers.createAnotherMockVisit;
import static com.example.smartgarage.Helpers.createMockVisit;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTests {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitServiceImpl visitService;


    @Test
    void getAll_Should_ReturnListOfObjects() {
        List<Visit> mockList = new ArrayList<>();
        mockList.add(createMockVisit());

        Mockito.when(visitRepository.findAll()).thenReturn(mockList);
        List<Visit> result = visitService.getAll();

        assertEquals(mockList.size(), result.size());
        Mockito.verify(visitRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findById_Should_ReturnObject() {
        Visit mockVisit = createMockVisit();
        Mockito.when(visitRepository.findById(mockVisit.getId()))
                .thenReturn(Optional.of(mockVisit));

        Optional<Visit> result = visitService.getById(mockVisit.getId());

        assertTrue(result.isPresent());
        assertEquals(mockVisit.getId(), result.get().getId());
        Mockito.verify(visitRepository, Mockito.times(1)).findById(mockVisit.getId());
    }

    @Test
    void testSave() {
        Visit visit = createMockVisit();
        visitService.save(visit);
        Mockito.verify(visitRepository).save(visit);
    }


    @Test
    void testGetAllByUsername() {
        Visit visit = createMockVisit();
        Visit visit1 = createAnotherMockVisit();
        List<Visit> visits = Arrays.asList(visit, visit1);

        Mockito.when(visitRepository.findAll()).thenReturn(visits);

        List<Visit> actualVisits = visitService.getAllByUsername("MockUsername");

        Assertions.assertEquals(2, visits.size());
        for (Visit visit2 : actualVisits) {
            Assertions.assertEquals("MockUsername", visit2.getVehicle().getUser().getUsername());
        }
        Mockito.verify(visitRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testToggleArchivedStatus() {
        Visit visit = new Visit();
        visit.setArchived(false);

        Visit result = visitService.toggleArchivedStatus(visit);
        assertSame(visit, result);

        assertTrue(visit.isArchived());

        result = visitService.toggleArchivedStatus(visit);
        assertSame(visit, result);

        assertFalse(visit.isArchived());
    }
}
