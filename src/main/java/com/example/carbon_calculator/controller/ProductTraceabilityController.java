package com.example.carbon_calculator.controller;

import com.example.carbon_calculator.entity.*;
import com.example.carbon_calculator.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/traceability")
public class ProductTraceabilityController {

    private final ProductRepository productRepository;
    private final BatchRepository batchRepository;
    private final StageTransferRepository stageTransferRepository;
    private final TransportLegRepository transportLegRepository;

    public ProductTraceabilityController(
            ProductRepository productRepository,
            BatchRepository batchRepository,
            StageTransferRepository stageTransferRepository,
            TransportLegRepository transportLegRepository) {
        this.productRepository = productRepository;
        this.batchRepository = batchRepository;
        this.stageTransferRepository = stageTransferRepository;
        this.transportLegRepository = transportLegRepository;
    }

    // --- Products ---
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productRepository.save(product));
    }

    @GetMapping("/products/organization/{orgId}")
    public ResponseEntity<List<Product>> getProductsByOrg(@PathVariable Long orgId) {
        return ResponseEntity.ok(productRepository.findByOrganizationId(orgId));
    }

    // --- Batches ---
    @PostMapping("/batches")
    public ResponseEntity<Batch> createBatch(@RequestBody Batch batch) {
        return ResponseEntity.ok(batchRepository.save(batch));
    }

    @GetMapping("/batches/product/{productId}")
    public ResponseEntity<List<Batch>> getBatchesByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(batchRepository.findByProductId(productId));
    }

    // --- Stage Transfers (Supply Chain Journey) ---
    @PostMapping("/transfers")
    public ResponseEntity<StageTransfer> recordTransfer(@RequestBody StageTransfer transfer) {
        return ResponseEntity.ok(stageTransferRepository.save(transfer));
    }

    @GetMapping("/transfers/batch/{batchId}")
    public ResponseEntity<List<StageTransfer>> getBatchTransfers(@PathVariable Long batchId) {
        return ResponseEntity.ok(stageTransferRepository.findByBatchId(batchId));
    }

    // --- Transport Legs ---
    @PostMapping("/transport")
    public ResponseEntity<TransportLeg> recordTransport(@RequestBody TransportLeg leg) {
        // Freight calculation formula: tonne-km * factor (mocked factor value 0.15 kg CO2e per tonne-km for demonstration)
        if (leg.getDistanceKm() != null && leg.getShippedMassTonnes() != null) {
            double tonneKm = leg.getDistanceKm() * leg.getShippedMassTonnes();
            leg.setCalculatedEmission(tonneKm * 0.15);
        }
        return ResponseEntity.ok(transportLegRepository.save(leg));
    }

    @GetMapping("/transport/batch/{batchId}")
    public ResponseEntity<List<TransportLeg>> getBatchTransport(@PathVariable Long batchId) {
        return ResponseEntity.ok(transportLegRepository.findByBatchId(batchId));
    }
}