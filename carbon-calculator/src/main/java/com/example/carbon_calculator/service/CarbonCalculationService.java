package com.example.carbon_calculator.service;

import com.example.carbon_calculator.entity.ActivityEntry;
import com.example.carbon_calculator.entity.Organization;
import com.example.carbon_calculator.repository.ActivityEntryRepository;
import com.example.carbon_calculator.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarbonCalculationService {

    private final ActivityEntryRepository activityRepository;
    private final OrganizationRepository orgRepository;

    public CarbonCalculationService(ActivityEntryRepository activityRepository, OrganizationRepository orgRepository) {
        this.activityRepository = activityRepository;
        this.orgRepository = orgRepository;
    }

    public List<ActivityEntry> getAllActivities() {
        return activityRepository.findAll();
    }

    public ActivityEntry calculateAndSave(String activityType, Double quantity, String unit) {
        // Ensure default organization exists
        Organization org = orgRepository.findAll().stream().findFirst().orElseGet(() -> {
            Organization newOrg = new Organization();
            newOrg.setName("GreenManufacturing Corp");
            return orgRepository.save(newOrg);
        });

        // Simple calculation factors (GHG Protocol aligned)
        double emissionFactor = activityType.equals("ELECTRICITY") ? 0.72 : 1.5;
        double calculatedEmission = quantity * emissionFactor;

        ActivityEntry entry = new ActivityEntry();
        entry.setActivityType(activityType);
        entry.setQuantity(quantity);
        entry.setUnit(unit);
        entry.setCalculatedEmission(calculatedEmission);
        entry.setActivityDate(LocalDate.now());
        entry.setOrganization(org);

        return activityRepository.save(entry);
    }
}	