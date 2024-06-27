package com.sesame.inventorymanagement.services;

import com.sesame.inventorymanagement.entities.Product;
import com.sesame.inventorymanagement.repositories.ProductRepository;
import com.sesame.inventorymanagement.utils.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product updatedProduct) {
        Product product = productRepository.findById(updatedProduct.getProductId()).orElseThrow(NotFoundException::new);
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setStockQuantity(updatedProduct.getStockQuantity());
        return productRepository.save(product);
    }

    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

}
