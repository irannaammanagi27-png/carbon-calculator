package com.example.carbon_calculator.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transport_legs")
public class TransportLeg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String legIdentifier;
    private String origin;
    private String destination;
    private String mode; // e.g., ROAD, RAIL, OCEAN, AIR
    private Double distanceKm;
    private Double shippedMassTonnes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    private Double calculatedEmission;

    public TransportLeg() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLegIdentifier() { return legIdentifier; }
    public void setLegIdentifier(String legIdentifier) { this.legIdentifier = legIdentifier; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Double getShippedMassTonnes() { return shippedMassTonnes; }
    public void setShippedMassTonnes(Double shippedMassTonnes) { this.shippedMassTonnes = shippedMassTonnes; }

    public Batch getBatch() { return batch; }
    public void setBatch(Batch batch) { this.batch = batch; }

    public Double getCalculatedEmission() { return calculatedEmission; }
    public void setCalculatedEmission(Double calculatedEmission) { this.calculatedEmission = calculatedEmission; }
}