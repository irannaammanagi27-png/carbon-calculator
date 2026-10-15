package com.example.carbon_calculator.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "supply_batch")
public class SupplyBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String batchNumber;

    private String supplierName;

    @Column(nullable = false)
    private Double quantity;

    private LocalDate batchDate;

    @PrePersist
    public void prePersist() {
        if (this.batchDate == null) {
            this.batchDate = LocalDate.now();
        }
        if (this.supplierName == null || this.supplierName.trim().isEmpty()) {
            this.supplierName = "GreenTier Supplier";
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public LocalDate getBatchDate() { return batchDate; }
    public void setBatchDate(LocalDate batchDate) { this.batchDate = batchDate; }
}