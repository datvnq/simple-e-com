package com.example.simpleecom.controller;

import com.example.simpleecom.entity.Product;
import com.example.simpleecom.entity.ProductCategory;
import com.example.simpleecom.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/product-categories")
    public List<ProductCategory> getAllProductCategories() {
        return productService.getAllProductCategories();
    }
}
