package com.sesame.inventorymanagement.services;

import com.sesame.inventorymanagement.entities.Customer;
import com.sesame.inventorymanagement.repositories.CustomerRepository;
import com.sesame.inventorymanagement.utils.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public void update(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(NotFoundException::new);
        customerRepository.save(customer);
    }

    public void delete(Long customerId) {
        customerRepository.deleteById(customerId);
    }
}
