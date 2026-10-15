package com.example.carbon_calculator.repository;

import com.example.carbon_calculator.entity.TransportLeg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportLegRepository extends JpaRepository<TransportLeg, Long> {
    List<TransportLeg> findByBatchId(Long batchId);
}