package com.example.carbon_calculator.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "batch_allocation")
public class BatchAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_batch_id", nullable = false)
    private ProductBatch productBatch;

    @ManyToOne
    @JoinColumn(name = "activity_entry_id", nullable = false)
    private ActivityEntry activityEntry;

    @Column(nullable = false)
    private double allocatedEmission;

    public BatchAllocation() {}

    public BatchAllocation(Long id, ProductBatch productBatch, ActivityEntry activityEntry, double allocatedEmission) {
        this.id = id;
        this.productBatch = productBatch;
        this.activityEntry = activityEntry;
        this.allocatedEmission = allocatedEmission;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ProductBatch getProductBatch() { return productBatch; }
    public void setProductBatch(ProductBatch productBatch) { this.productBatch = productBatch; }

    public ActivityEntry getActivityEntry() { return activityEntry; }
    public void setActivityEntry(ActivityEntry activityEntry) { this.activityEntry = activityEntry; }

    public double getAllocatedEmission() { return allocatedEmission; }
    public void setAllocatedEmission(double allocatedEmission) { this.allocatedEmission = allocatedEmission; }
}