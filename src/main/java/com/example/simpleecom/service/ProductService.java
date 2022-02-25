package com.example.simpleecom.service;

import com.example.simpleecom.entity.Product;
import com.example.simpleecom.entity.ProductCategory;
import com.example.simpleecom.repository.ProductCategoryRepository;
import com.example.simpleecom.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }
}
