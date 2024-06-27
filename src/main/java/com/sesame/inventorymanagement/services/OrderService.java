package com.sesame.inventorymanagement.services;

import com.sesame.inventorymanagement.entities.Order;
import com.sesame.inventorymanagement.repositories.CustomerRepository;
import com.sesame.inventorymanagement.repositories.OrderRepository;
import com.sesame.inventorymanagement.utils.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(final OrderRepository orderRepository,
            final CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order create(Order order) {
        return orderRepository.save(order);
    }

    public Order update(Order updatedOrder) {
        Order order = orderRepository.findById(updatedOrder.getOrdersId()).orElseThrow(NotFoundException::new);
        order.setOrderDate(updatedOrder.getOrderDate());
        order.setTotalPrice(updatedOrder.getTotalPrice());
        order.setCustomer(updatedOrder.getCustomer());
        return orderRepository.save(order);
    }

    public void delete(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
