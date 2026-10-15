package com.example.carbon_calculator.repository;

import com.example.carbon_calculator.entity.StageTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageTransferRepository extends JpaRepository<StageTransfer, Long> {
    List<StageTransfer> findByBatchId(Long batchId);
}