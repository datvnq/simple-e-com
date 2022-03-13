package com.example.simpleecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private String name;
    private BigDecimal unitPrice;
    private int quantity;
    private Long productId;
}
