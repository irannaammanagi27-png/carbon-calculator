package com.example.carbon_calculator.config;

import com.example.carbon_calculator.entity.ActivityEntry;
import com.example.carbon_calculator.entity.Organization;
import com.example.carbon_calculator.repository.ActivityEntryRepository;
import com.example.carbon_calculator.repository.OrganizationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private final OrganizationRepository orgRepository;
    private final ActivityEntryRepository activityRepository;

    public DataSeeder(OrganizationRepository orgRepository, ActivityEntryRepository activityRepository) {
        this.orgRepository = orgRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Seed default organization safely
        Organization org = orgRepository.findAll().stream().findFirst().orElseGet(() -> {
            Organization newOrg = new Organization();
            newOrg.setName("GreenManufacturing Corp");
            return orgRepository.save(newOrg);
        });

        // Seed initial activity entry if table is empty
        if (activityRepository.count() == 0) {
            ActivityEntry entry = new ActivityEntry();
            entry.setActivityType("ELECTRICITY");
            entry.setQuantity(1250.0);
            entry.setUnit("kWh");
            entry.setCalculatedEmission(1250.0 * 0.72);
            entry.setActivityDate(LocalDate.now());
            entry.setOrganization(org);
            activityRepository.save(entry);
        }
    }
}