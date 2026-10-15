package com.example.carbon_calculator.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "stage_transfers")
public class StageTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stageName; // e.g., SUPPLIER, MANUFACTURER, DISTRIBUTOR, RETAILER
    private LocalDate transferDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_org_id")
    private Organization senderOrganization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_org_id", nullable = false)
    private Organization receiverOrganization;

    public StageTransfer() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStageName() { return stageName; }
    public void setStageName(String stageName) { this.stageName = stageName; }

    public LocalDate getTransferDate() { return transferDate; }
    public void setTransferDate(LocalDate transferDate) { this.transferDate = transferDate; }

    public Batch getBatch() { return batch; }
    public void setBatch(Batch batch) { this.batch = batch; }

    public Organization getSenderOrganization() { return senderOrganization; }
    public void setSenderOrganization(Organization senderOrganization) { this.senderOrganization = senderOrganization; }

    public Organization getReceiverOrganization() { return receiverOrganization; }
    public void setReceiverOrganization(Organization receiverOrganization) { this.receiverOrganization = receiverOrganization; }
}