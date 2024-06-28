package com.sesame.inventorymanagement.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "orders_id")
    private Long ordersId;

    @Column(name = "totalPrice")
    private Float totalPrice;

    @Column(name = "orderDate")
    private Date orderDate;

    @Column(name = "status")
    public Status status;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<ProductOrder> productOrders;

    public Order() {
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

}
