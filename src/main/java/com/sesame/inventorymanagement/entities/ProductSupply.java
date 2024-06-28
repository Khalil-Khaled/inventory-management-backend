package com.sesame.inventorymanagement.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product_supply")
public class ProductSupply {

    @Id
    @GeneratedValue
    @Column(name = "product_supply_id")
    private Long productSupplyId;

    @Column(name = "stockQuantity")
    private Long stockQuantity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supply_id")
    Supply supply;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    Product product;

    public ProductSupply() {
    }

    public Long getProductSupplyId() {
        return productSupplyId;
    }

    public void setProductSupplyId(Long productSupplyId) {
        this.productSupplyId = productSupplyId;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public Product getProduct() {
        return product;
    }

    public void setproduct(Product product) {
        this.product = product;
    }

}
