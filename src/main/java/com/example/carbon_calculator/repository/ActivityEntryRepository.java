package com.example.carbon_calculator.repository;

import com.example.carbon_calculator.entity.ActivityEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityEntryRepository extends JpaRepository<ActivityEntry, Long> {
}