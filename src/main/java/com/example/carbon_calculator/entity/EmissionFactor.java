package com.example.carbon_calculator.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "emission_factors")
public class EmissionFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String factorId; // e.g., "electricity-grid-IN"

    private String sourcePublisher;
    private String datasetVersion;
    private Integer factorYear;
    private String geography;
    private Double factorValue;
    private String unit;

    public EmissionFactor() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFactorId() { return factorId; }
    public void setFactorId(String factorId) { this.factorId = factorId; }

    public String getSourcePublisher() { return sourcePublisher; }
    public void setSourcePublisher(String sourcePublisher) { this.sourcePublisher = sourcePublisher; }

    public String getDatasetVersion() { return datasetVersion; }
    public void setDatasetVersion(String datasetVersion) { this.datasetVersion = datasetVersion; }

    public Integer getFactorYear() { return factorYear; }
    public void setFactorYear(Integer factorYear) { this.factorYear = factorYear; }

    public String getGeography() { return geography; }
    public void setGeography(String geography) { this.geography = geography; }

    public Double getFactorValue() { return factorValue; }
    public void setFactorValue(Double factorValue) { this.factorValue = factorValue; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
}