package com.sesame.inventorymanagement.entities.DTO;

import java.util.List;

import com.sesame.inventorymanagement.entities.Product;

public class OrderDTO {

    Long orderId;
    List<Product> products;

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

}
