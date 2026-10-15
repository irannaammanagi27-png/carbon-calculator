package com.example.carbon_calculator.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "activity_entry")
public class ActivityEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String activityType;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private String unit;

    private Double calculatedEmission;

    private LocalDate activityDate;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @PrePersist
    public void prePersist() {
        if (this.activityDate == null) {
            this.activityDate = LocalDate.now();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Double getCalculatedEmission() { return calculatedEmission; }
    public void setCalculatedEmission(Double calculatedEmission) { this.calculatedEmission = calculatedEmission; }

    public LocalDate getActivityDate() { return activityDate; }
    public void setActivityDate(LocalDate activityDate) { this.activityDate = activityDate; }

    public Organization getOrganization() { return organization; }
    public void setOrganization(Organization organization) { this.organization = organization; }
}