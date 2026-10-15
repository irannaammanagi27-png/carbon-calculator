package com.example.carbon_calculator.controller;

import com.example.carbon_calculator.entity.ActivityEntry;
import com.example.carbon_calculator.repository.ActivityEntryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ReportController {

    private final ActivityEntryRepository activityRepository;

    public ReportController(ActivityEntryRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getEmissionSummary() {
        List<ActivityEntry> entries = activityRepository.findAll();
        
        double totalCarbon = entries.stream()
                .mapToDouble(e -> e.getCalculatedEmission() != null ? e.getCalculatedEmission() : 0.0)
                .sum();

        double scope2Total = entries.stream()
                .filter(e -> "ELECTRICITY".equalsIgnoreCase(e.getActivityType()))
                .mapToDouble(e -> e.getCalculatedEmission() != null ? e.getCalculatedEmission() : 0.0)
                .sum();

        double scope3Total = totalCarbon - scope2Total;

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalCarbon", totalCarbon);
        summary.put("scope2Total", scope2Total);
        summary.put("scope3Total", scope3Total);
        summary.put("totalEntries", entries.size());

        return ResponseEntity.ok(summary);
    }
}