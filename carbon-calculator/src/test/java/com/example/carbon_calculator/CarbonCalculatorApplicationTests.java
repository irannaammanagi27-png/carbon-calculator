package com.example.carbon_calculator;

import com.example.carbon_calculator.entity.ActivityEntry;
import com.example.carbon_calculator.entity.Organization;
import com.example.carbon_calculator.repository.ActivityEntryRepository;
import com.example.carbon_calculator.repository.OrganizationRepository;
import com.example.carbon_calculator.service.CarbonCalculationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CarbonCalculationServiceTest {

    @Mock
    private ActivityEntryRepository activityRepository;

    @Mock
    private OrganizationRepository orgRepository;

    @InjectMocks
    private CarbonCalculationService calculationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateAndSaveElectricity() {
        Organization mockOrg = new Organization();
        mockOrg.setId(1L);
        mockOrg.setName("GreenManufacturing Corp");

        when(orgRepository.findAll()).thenReturn(Collections.singletonList(mockOrg));
        when(activityRepository.save(any(ActivityEntry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ActivityEntry result = calculationService.calculateAndSave("ELECTRICITY", 100.0, "kWh");

        assertNotNull(result);
        assertEquals("ELECTRICITY", result.getActivityType());
        assertEquals(100.0, result.getQuantity());
        // 100.0 * 0.72 emission factor = 72.0
        assertEquals(72.0, result.getCalculatedEmission());
        verify(activityRepository, times(1)).save(any(ActivityEntry.class));
    }
}