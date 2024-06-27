package com.sesame.inventorymanagement.entities;

import javax.persistence.*;
import java.util.Date;
// import java.util.List;
import java.util.List;

@Entity
@Table(name = "supply")
public class Supply {

    @Id
    @GeneratedValue
    @Column(name = "supply_id")
    private Long supplyId;

    @Column(name = "supplyDate")
    private Date supplyDate;

    @Column(name = "status")
    public Status status;

    @ManyToMany
    @JoinTable(name = "supply_product", joinColumns = @JoinColumn(name = "supply_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    public Supply() {
    }

    public String getStatus() {
        switch (this.status) {
            case DRAFT:
                return "draft";
            case IN_PROGRESS:
                return "in progress";
            case COMPLETED:
                return "completed";
            default:
                return "draft";
        }
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public Date getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(Date supplyDate) {
        this.supplyDate = supplyDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
