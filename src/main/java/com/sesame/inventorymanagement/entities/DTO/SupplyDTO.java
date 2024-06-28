
package com.sesame.inventorymanagement.entities.DTO;

import java.util.List;

import com.sesame.inventorymanagement.entities.Product;

public class SupplyDTO {
    Long supplyId;
    List<Product> products;

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}