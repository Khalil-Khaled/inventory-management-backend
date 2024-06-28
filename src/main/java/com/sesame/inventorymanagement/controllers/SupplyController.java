package com.sesame.inventorymanagement.controllers;

import com.sesame.inventorymanagement.entities.Product;
import com.sesame.inventorymanagement.entities.ProductSupply;
import com.sesame.inventorymanagement.entities.Status;
import com.sesame.inventorymanagement.entities.Supply;
import com.sesame.inventorymanagement.entities.DTO.SupplyDTO;
import com.sesame.inventorymanagement.repositories.ProductRepository;
import com.sesame.inventorymanagement.repositories.ProductSupplyRepository;
import com.sesame.inventorymanagement.repositories.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/supplies")
public class SupplyController {

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSupplyRepository productSupplyRepository;

    @GetMapping
    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll().stream().map(supply -> {
            List<ProductSupply> productSupplies = productSupplyRepository.findAll().stream()
                    .filter(productSupply -> productSupply.getSupply().getSupplyId() == supply.getSupplyId())
                    .collect(Collectors.toList());
            supply.setProductSupplies(productSupplies);
            return supply;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supply> getSupplyById(@PathVariable Long id) {
        return supplyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Supply createSupply(@RequestBody SupplyDTO supply) {
        Supply supplyDB = new Supply();
        supplyDB.setSupplyDate(new Date());
        supplyDB.setStatus(Status.IN_PROGRESS);
        Supply savedSupply = supplyRepository.save(supplyDB);
        List<Product> products = supply.getProducts();
        List<ProductSupply> productSupplies = products.stream().map(product -> {
            ProductSupply productSupply = new ProductSupply();
            productSupply.setSupply(savedSupply);
            productSupply.setproduct(product);
            productSupply.setStockQuantity(product.getStockQuantity());
            return productSupply;
        }).collect(Collectors.toList());
        supplyDB.setProductSupplies(productSupplies);
        return supplyRepository.save(supplyDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supply> updateSupply(@PathVariable Long id, @RequestBody Supply supplyDetails) {
        return supplyRepository.findById(id)
                .map(supply -> {
                    supply.setSupplyDate(supplyDetails.getSupplyDate());
                    supply.setStatus(supplyDetails.status);
                    return ResponseEntity.ok(supplyRepository.save(supply));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/confirmSupply")
    public ResponseEntity<Supply> confirmSupply(@PathVariable Long id) {
        return supplyRepository.findById(id)
                .map(supply -> {
                    supply.setStatus(Status.COMPLETED);
                    supply.getProductSupplies().stream().forEach(productSupply -> {
                        productRepository.findById(productSupply.getProduct().getProductId()).map(productDB -> {
                            productDB.setStockQuantity(productDB.getStockQuantity() + productSupply.getStockQuantity());
                            return productRepository.save(productDB);
                        });
                    });
                    return ResponseEntity.ok(supplyRepository.save(supply));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        return supplyRepository.findById(id)
                .map(supply -> {
                    supplyRepository.delete(supply);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}