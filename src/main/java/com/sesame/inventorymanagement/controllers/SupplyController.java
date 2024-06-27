package com.sesame.inventorymanagement.controllers;

import com.sesame.inventorymanagement.entities.Status;
import com.sesame.inventorymanagement.entities.Supply;
import com.sesame.inventorymanagement.repositories.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplies")
public class SupplyController {

    @Autowired
    private SupplyRepository supplyRepository;

    @GetMapping
    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supply> getSupplyById(@PathVariable Long id) {
        return supplyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Supply createSupply(@RequestBody Supply supply) {
        supply.setStatus(Status.IN_PROGRESS);
        return supplyRepository.save(supply);
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