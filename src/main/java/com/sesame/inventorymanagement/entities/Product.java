package com.sesame.inventorymanagement.entities;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Float price;

    @Column(name = "stockQuantity")
    private Long stockQuantity;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOrder> productOrders;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<ProductSupply> productSupplies;

    public Product() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<ProductOrder> getOrders() {
        return productOrders;
    }

    public void setOrders(List<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    public List<ProductSupply> getSupplies() {
        return productSupplies;
    }

    public void setSupplies(List<ProductSupply> productSupplies) {
        this.productSupplies = productSupplies;
    }
}
