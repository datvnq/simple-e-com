package com.example.simpleecom.service;

import com.example.simpleecom.dto.ProductCategoryDto;
import com.example.simpleecom.dto.ProductDto;
import com.example.simpleecom.entity.Product;
import com.example.simpleecom.entity.ProductCategory;
import com.example.simpleecom.repository.ProductCategoryRepository;
import com.example.simpleecom.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public Page<ProductDto> getAllProducts(Long categoryId, String keyword, Pageable pageable) {

        Page<Product> pageProducts;

        if (categoryId == null) {
            if (keyword == null) {
                pageProducts = productRepository.findAll(pageable);
            }
            else {
                pageProducts = productRepository.findByNameContaining(keyword, pageable);
            }
        }
        else {
            if (keyword == null) {
                pageProducts = productRepository.findByCategoryId(categoryId, pageable);
            }
            else {
                pageProducts = productRepository.findByCategoryIdAndNameContaining(categoryId, keyword, pageable);
            }
        }
        Page<ProductDto> pageProductsDto = pageProducts.map(this::productEntityToDto);

        return pageProductsDto;
    }

    public ProductDto getProductById(Long id) {
        return productEntityToDto(productRepository.findById(id).orElseThrow());
    }

    public Page<ProductDto> getRelatedProducts(Long categoryId, Long productId, Pageable pageable) {

        Page<Product> pageProducts;
        pageProducts = productRepository.findByCategoryIdAndIdNot(categoryId, productId, pageable);

        Page<ProductDto> pageProductsDto = pageProducts.map(this::productEntityToDto);

        return pageProductsDto;
    }

    public Long updateProduct(Long id, ProductDto productDto) {
        Product tempProduct = productRepository.findById(id).orElseThrow();
        tempProduct.setName(productDto.getName());
        tempProduct.setDescription(productDto.getDescription());
        tempProduct.setUnitPrice(productDto.getUnitPrice());
        tempProduct.setUnitInStock(productDto.getUnitInStock());
        tempProduct.setImageUrl(productDto.getImageUrl());
        tempProduct.setCategory(productCategoryRepository.findById(productDto.getCategoryId()).orElseThrow());
        Product updatedProduct = productRepository.save(tempProduct);

        return updatedProduct.getId();
    }

    public Long createProduct(ProductDto productDto) {
        productRepository.save(productDtoToEntity(productDto));
        Product tempProduct = productRepository.findByName(productDto.getName()).orElseThrow();
        return tempProduct.getId();
    }

    public Long deleteProduct(Long productId) {
        Product tempProduct = productRepository.findById(productId).orElseThrow();
        productRepository.delete(tempProduct);
        return productId;
    }

    public List<ProductCategoryDto> getAllProductCategories() {
        return productCategoryRepository.findAll()
                .stream()
                .map(this::productCategoryEntityToDto)
                .collect(Collectors.toList());
    }

    public ProductCategory getCategoryById(Long id) {
        return productCategoryRepository.findById(id).orElseThrow();
    }

    public Long createProductCategory(ProductCategoryDto productCategoryDto) {
        productCategoryRepository.save(productCategoryDtoToEntity(productCategoryDto));
        ProductCategory tempProductCategory = productCategoryRepository.findByCategoryName(productCategoryDto.getCategoryName()).orElseThrow();
        return tempProductCategory.getId();
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

    private Product productDtoToEntity(ProductDto productDto) {
        Product tempProduct = new Product();
        tempProduct.setName(productDto.getName());
        tempProduct.setDescription(productDto.getDescription());
        tempProduct.setImageUrl(productDto.getImageUrl());
        tempProduct.setUnitPrice(productDto.getUnitPrice());
        tempProduct.setUnitInStock(productDto.getUnitInStock());
        tempProduct.setCategory(getCategoryById(productDto.getCategoryId()));
        return tempProduct;
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

    private ProductCategory productCategoryDtoToEntity(ProductCategoryDto productCategoryDto) {
        ProductCategory tempProductCategory = new ProductCategory();
        tempProductCategory.setCategoryName(productCategoryDto.getCategoryName());
        tempProductCategory.setProducts(new HashSet<>());
        return tempProductCategory;
    }
}
