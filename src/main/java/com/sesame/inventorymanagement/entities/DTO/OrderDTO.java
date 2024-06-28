package com.sesame.inventorymanagement.entities.DTO;

import java.util.List;

import com.sesame.inventorymanagement.entities.Customer;
import com.sesame.inventorymanagement.entities.Product;

public class OrderDTO {

    Long orderId;
    List<Product> products;
    Customer customer;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
