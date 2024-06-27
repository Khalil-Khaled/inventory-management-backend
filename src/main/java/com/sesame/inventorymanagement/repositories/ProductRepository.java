package com.sesame.inventorymanagement.repositories;

import com.sesame.inventorymanagement.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

