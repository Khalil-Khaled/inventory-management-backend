package com.sesame.inventorymanagement.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<ProductSupply> productSupplies;

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

    public List<ProductSupply> getProductSupplies() {
        return productSupplies;
    }

    public void setProductSupplies(List<ProductSupply> productSupplies) {
        this.productSupplies = productSupplies;
    }

}
