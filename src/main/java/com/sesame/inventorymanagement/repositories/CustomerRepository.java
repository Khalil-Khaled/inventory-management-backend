package com.sesame.inventorymanagement.repositories;

import com.sesame.inventorymanagement.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
