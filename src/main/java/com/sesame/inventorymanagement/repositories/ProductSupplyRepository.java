package com.sesame.inventorymanagement.repositories;

import com.sesame.inventorymanagement.entities.ProductSupply;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSupplyRepository extends JpaRepository<ProductSupply, Long> {
}

