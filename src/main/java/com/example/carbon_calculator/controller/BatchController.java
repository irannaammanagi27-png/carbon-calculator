package com.example.carbon_calculator.controller;

import com.example.carbon_calculator.entity.SupplyBatch;
import com.example.carbon_calculator.repository.SupplyBatchRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class BatchController {

    private final SupplyBatchRepository batchRepository;

    public BatchController(SupplyBatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    @GetMapping
    public ResponseEntity<List<SupplyBatch>> getAllBatches() {
        return ResponseEntity.ok(batchRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<SupplyBatch> createBatch(@RequestBody SupplyBatch batch) {
        SupplyBatch savedBatch = batchRepository.save(batch);
        return ResponseEntity.ok(savedBatch);
    }
}