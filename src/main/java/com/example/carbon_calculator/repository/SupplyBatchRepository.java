package com.example.carbon_calculator.repository;

import com.example.carbon_calculator.entity.SupplyBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyBatchRepository extends JpaRepository<SupplyBatch, Long> {
}