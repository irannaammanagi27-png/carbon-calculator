package com.example.carbon_calculator.controller;

import com.example.carbon_calculator.entity.ActivityEntry;
import com.example.carbon_calculator.service.CarbonCalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ActivityController {

    private final CarbonCalculationService calculationService;

    public ActivityController(CarbonCalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping
    public ResponseEntity<List<ActivityEntry>> getAllActivities() {
        return ResponseEntity.ok(calculationService.getAllActivities());
    }

    @PostMapping
    public ResponseEntity<ActivityEntry> createActivity(@RequestBody ActivityEntry request) {
        ActivityEntry savedEntry = calculationService.calculateAndSave(
            request.getActivityType(),
            request.getQuantity(),
            request.getUnit()
        );
        return ResponseEntity.ok(savedEntry);
    }
}