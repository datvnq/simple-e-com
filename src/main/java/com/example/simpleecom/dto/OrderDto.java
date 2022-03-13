package com.example.simpleecom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String orderTrackingNumber;
    private int totalQuantity;
    private BigDecimal totalPrice;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String note;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateCreated;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
    private String customerPhoneNumber;
    private List<OrderItemDto> orderItemList;
}
