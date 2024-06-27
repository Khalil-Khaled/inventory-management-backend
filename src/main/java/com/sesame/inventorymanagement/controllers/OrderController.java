package com.sesame.inventorymanagement.controllers;

import com.sesame.inventorymanagement.entities.Order;
import com.sesame.inventorymanagement.entities.Product;
import com.sesame.inventorymanagement.entities.Status;
import com.sesame.inventorymanagement.repositories.OrderRepository;
import com.sesame.inventorymanagement.repositories.ProductRepository;
import com.sesame.inventorymanagement.utils.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

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
    public Order createOrder(@RequestBody Order order) {
        float totalPrice = 0.0f;
        for (Product product : order.getProducts()) {
            // Fetch product from database
            Product productDB = productRepository.findById(product.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found with id: " + product.getProductId()));
    
            // Update product stock quantity
            long orderedQuantity = product.getStockQuantity();
            productDB.setStockQuantity(productDB.getStockQuantity() - orderedQuantity);
    
            // Calculate product total price and accumulate total order price
            totalPrice += productDB.getPrice() * orderedQuantity;
    
            // Save updated product
            productRepository.save(productDB);
        }
        // order.getProducts().stream().map(product -> {
        //     Product productDB = productRepository.findById(product.getProductId())
        //             .orElseThrow(() -> new NotFoundException());
        //     productDB.setStockQuantity(productDB.getStockQuantity() - product.getStockQuantity());
        //     order.setTotalPrice(order.getTotalPrice() + productDB.getPrice() * product.getStockQuantity());
        //     return productRepository.save(productDB);
        // });
        order.setTotalPrice(totalPrice);
        order.setOrderDate(new Date());
        order.setStatus(Status.DRAFT);
        return orderRepository.save(order);
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
                            order.getProducts().add(product);
                            return ResponseEntity.ok(orderRepository.save(order));
                        })
                        .orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{orderId}/products/{productId}")
    public ResponseEntity<Order> removeProductFromOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        return orderRepository.findById(orderId)
                .map(order -> productRepository.findById(productId)
                        .map(product -> {
                            order.getProducts().remove(product);
                            return ResponseEntity.ok(orderRepository.save(order));
                        })
                        .orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.notFound().build());
    }
}
