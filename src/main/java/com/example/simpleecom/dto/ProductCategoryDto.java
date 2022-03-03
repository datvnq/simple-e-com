package com.example.simpleecom.dto;

import com.example.simpleecom.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDto {
    private Long id;
    private String categoryName;
    private Set<ProductDto> products;
}
