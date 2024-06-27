package com.sesame.inventorymanagement.controllers;

import com.sesame.inventorymanagement.entities.Customer;
import com.sesame.inventorymanagement.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.create(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/updateCustomer/{customerId}")
    public ResponseEntity<Long> updateCustomer(@PathVariable(name = "customerId") Long customerId) {
        customerService.update(customerId);
        return ResponseEntity.ok(customerId);
    }

    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(name = "customerId") Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }
}
