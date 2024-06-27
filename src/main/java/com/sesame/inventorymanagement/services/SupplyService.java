package com.sesame.inventorymanagement.services;

import com.sesame.inventorymanagement.entities.Order;
import com.sesame.inventorymanagement.repositories.OrderRepository;
import com.sesame.inventorymanagement.repositories.ProductRepository;
import com.sesame.inventorymanagement.utils.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public SupplyService(final OrderRepository orderRepository,
            final ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
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
