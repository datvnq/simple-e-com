package com.example.simpleecom.service;

import com.example.simpleecom.dto.ProductCategoryDto;
import com.example.simpleecom.dto.ProductDto;
import com.example.simpleecom.entity.Product;
import com.example.simpleecom.entity.ProductCategory;
import com.example.simpleecom.repository.ProductCategoryRepository;
import com.example.simpleecom.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::productEntityToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        return productEntityToDto(productRepository.findById(id).orElseThrow());
    }

    public List<ProductDto> getProductByCategoryId(Long id) {
        ProductCategoryDto tempProductCategoryDto = productCategoryEntityToDto(productCategoryRepository.findById(id).orElseThrow());
        return tempProductCategoryDto.getProducts().stream().collect(Collectors.toList());
    }

    public List<ProductDto> getProductsByKeyword(String keyword) {
        List<ProductDto> result = new ArrayList<>();
        List<ProductDto> allProducts = getAllProducts();
        if (keyword != null) {
            for (ProductDto tempProductDto : allProducts) {
                if (tempProductDto.getName().toLowerCase().contains(keyword.toLowerCase().trim())) {
                    result.add(tempProductDto);
                }
            }
        }
        return result;
    }

    public List<ProductCategoryDto> getAllProductCategories() {
        return productCategoryRepository.findAll()
                .stream()
                .map(this::productCategoryEntityToDto)
                .collect(Collectors.toList());
    }

    private ProductDto productEntityToDto(Product product) {
        ProductDto tempProductDto = new ProductDto();
        tempProductDto.setId(product.getId());
        tempProductDto.setCategoryId(product.getCategory().getId());
        tempProductDto.setCategoryName(product.getCategory().getCategoryName());
        tempProductDto.setName(product.getName());
        tempProductDto.setDescription(product.getDescription());
        tempProductDto.setUnitPrice(product.getUnitPrice());
        tempProductDto.setImageUrl(product.getImageUrl());
        tempProductDto.setUnitInStock(product.getUnitInStock());
        return tempProductDto;
    }

    private ProductCategoryDto productCategoryEntityToDto(ProductCategory productCategory) {
        ProductCategoryDto tempProductCategoryDto = new ProductCategoryDto();
        tempProductCategoryDto.setId(productCategory.getId());
        tempProductCategoryDto.setCategoryName(productCategory.getCategoryName());
        Set<ProductDto> productDtoSet = productCategory.getProducts()
                .stream()
                .map(this::productEntityToDto)
                .collect(Collectors.toSet());
        tempProductCategoryDto.setProducts(productDtoSet);
        return tempProductCategoryDto;
    }
}
