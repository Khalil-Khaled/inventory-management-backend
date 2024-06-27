package com.sesame.inventorymanagement.repositories;

import com.sesame.inventorymanagement.entities.Supply;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
