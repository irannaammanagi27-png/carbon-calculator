package com.example.carbon_calculator.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_batch")
public class ProductBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String batchNumber;

    @Column(nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    public ProductBatch() {}

    public ProductBatch(Long id, String batchNumber, String productName, Organization organization) {
        this.id = id;
        this.batchNumber = batchNumber;
        this.productName = productName;
        this.organization = organization;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Organization getOrganization() { return organization; }
    public void setOrganization(Organization organization) { this.organization = organization; }
}