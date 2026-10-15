package com.example.carbon_calculator.repository;

import com.example.carbon_calculator.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByOrganizationId(Long organizationId);
}