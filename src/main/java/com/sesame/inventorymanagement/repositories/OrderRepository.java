package com.sesame.inventorymanagement.repositories;

import com.sesame.inventorymanagement.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
