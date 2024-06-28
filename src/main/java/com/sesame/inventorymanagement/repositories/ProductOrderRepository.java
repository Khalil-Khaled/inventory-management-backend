package com.sesame.inventorymanagement.repositories;

import com.sesame.inventorymanagement.entities.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    
}
