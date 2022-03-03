package com.example.simpleecom.dto;

import com.example.simpleecom.entity.Customer;
import com.example.simpleecom.entity.Order;
import com.example.simpleecom.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Order order;
    private Set<OrderItem> orderItems;
}
