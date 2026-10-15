package com.example.carbon_calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.carbon_calculator.entity.BatchAllocation;

import java.util.List;

@Repository
public interface BatchAllocationRepository extends JpaRepository<BatchAllocation, Long> {
    List<BatchAllocation> findByProductBatchId(Long productBatchId);
}