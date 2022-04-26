package com.example.simpleecom.controller;

import com.example.simpleecom.dto.ProductCategoryDto;
import com.example.simpleecom.dto.ProductDto;
import com.example.simpleecom.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public Page<ProductDto> getAllProducts(@RequestParam(required = false) Long categoryId,
                                           @RequestParam(required = false) String keyword,
                                           Pageable pageable) {
        return productService.getAllProducts(categoryId, keyword, pageable);
    }

    @GetMapping("/product/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/relatedProducts/{categoryId}/{productId}")
    public Page<ProductDto> getRelatedProducts(@PathVariable Long categoryId,
                                               @PathVariable Long productId,
                                               Pageable pageable) {
        return productService.getRelatedProducts(categoryId, productId, pageable);
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

    @GetMapping("/productCategories")
    public List<ProductCategoryDto> getAllProductCategories() {
        return productService.getAllProductCategories();
    }

    @PostMapping("/productCategory")
    public Long createProductCategory(@RequestBody ProductCategoryDto productCategoryDto) {
        return productService.createProductCategory(productCategoryDto);
    }
}
