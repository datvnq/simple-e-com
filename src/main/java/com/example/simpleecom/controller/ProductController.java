package com.example.simpleecom.controller;

import com.example.simpleecom.dto.ProductCategoryDto;
import com.example.simpleecom.dto.ProductDto;
import com.example.simpleecom.entity.Product;
import com.example.simpleecom.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/productCategory/{id}")
    public List<ProductDto> getProductByCategoryId(@PathVariable Long id) {
        return productService.getProductByCategoryId(id);
    }

    @PutMapping("/product/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto);
    }

    @PostMapping("/product")
    public Long createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @DeleteMapping("/product/{id}")
    public Long deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/products/{keyword}")
    public List<ProductDto> getProductsByKeyword(@PathVariable String keyword) {
        return productService.getProductsByKeyword(keyword);
    }

    @GetMapping("/productCategories")
    public List<ProductCategoryDto> getAllProductCategories() {
        return productService.getAllProductCategories();
    }

    @PostMapping("/productCategory")
    public Long createProductCategory(@RequestBody ProductCategoryDto productCategoryDto) {
        return productService.createProductCategory(productCategoryDto);
    }
}
