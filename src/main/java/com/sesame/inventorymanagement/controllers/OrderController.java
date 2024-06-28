package com.sesame.inventorymanagement.controllers;

import com.sesame.inventorymanagement.entities.Order;
import com.sesame.inventorymanagement.entities.Product;
import com.sesame.inventorymanagement.entities.ProductOrder;
import com.sesame.inventorymanagement.entities.Status;
import com.sesame.inventorymanagement.entities.DTO.OrderDTO;
import com.sesame.inventorymanagement.repositories.OrderRepository;
import com.sesame.inventorymanagement.repositories.ProductOrderRepository;
import com.sesame.inventorymanagement.repositories.ProductRepository;
import com.sesame.inventorymanagement.utils.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderDTO orderDTO) {
        Order orderDB = new Order();
        orderDB.setOrderDate(new Date());
        orderDB.setStatus(Status.DRAFT);
        orderDB.setTotalPrice(0.0f);
        orderDB.setCustomer(orderDTO.getCustomer());
        Order savedOrder = orderRepository.save(orderDB);
        List<Product> products = orderDTO.getProducts();
        List<ProductOrder> productOrders = products.stream().map(product -> {
            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrder(savedOrder);
            productOrder.setProduct(product);
            Product productDB = productRepository.findById(product.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found with id: " + product.getProductId()));
            long orderedQuantity = product.getStockQuantity();
            productDB.setStockQuantity(productDB.getStockQuantity() - orderedQuantity);
            productOrder.setStockQuantity(orderedQuantity);
            savedOrder.setTotalPrice(savedOrder.getTotalPrice() + productDB.getPrice() * orderedQuantity);
            productRepository.save(productDB);
            return productOrder;
        }).collect(Collectors.toList());
        savedOrder.setProductOrders(productOrders);
        productOrderRepository.saveAll(productOrders);
        return orderRepository.save(savedOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setOrderDate(orderDetails.getOrderDate());
                    order.setStatus(orderDetails.status);
                    return ResponseEntity.ok(orderRepository.save(order));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    orderRepository.delete(order);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{orderId}/products/{productId}")
    public ResponseEntity<Order> addProductToOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        return orderRepository.findById(orderId)
                .map(order -> productRepository.findById(productId)
                        .map(product -> {
                            boolean productAlreadyInOrder = order.getProductOrders().stream()
                                    .anyMatch(po -> po.getProduct().getProductId().equals(productId));
                            if (!productAlreadyInOrder) {
                                ProductOrder productOrder = new ProductOrder();
                                productOrder.setOrder(order);
                                productOrder.setProduct(product);
                                productOrder.setStockQuantity(1L); // thiiisssss
                                order.getProductOrders().add(productOrder);
                                productOrderRepository.save(productOrder);
                            }
                            Order updatedOrder = orderRepository.save(order);
                            return ResponseEntity.ok(updatedOrder);
                        })
                        .orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{orderId}/products/{productId}")
    public ResponseEntity<Order> removeProductFromOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    ProductOrder productOrderToRemove = order.getProductOrders().stream()
                            .filter(po -> po.getProduct().getProductId().equals(productId))
                            .findFirst()
                            .orElseThrow(
                                    () -> new NotFoundException("ProductOrder not found for productId: " + productId));
                    order.getProductOrders().remove(productOrderToRemove);
                    Product product = productOrderToRemove.getProduct();
                    product.setStockQuantity(product.getStockQuantity() + productOrderToRemove.getStockQuantity());
                    productRepository.save(product);
                    Order updatedOrder = orderRepository.save(order);
                    return ResponseEntity.ok(updatedOrder);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
