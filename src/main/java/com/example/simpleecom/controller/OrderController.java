package com.example.simpleecom.controller;

import com.example.simpleecom.dto.OrderDto;
import com.example.simpleecom.dto.OrderItemDto;
import com.example.simpleecom.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public Page<OrderDto> getAllOrders(@RequestParam(required = false) String keyword,
                                       Pageable pageable) {
        return orderService.getAllOrders(keyword, pageable);
    }

    @GetMapping("/order/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/order/{id}")
    public Long updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        return orderService.updateOrder(id, orderDto);
    }

    @DeleteMapping("/order/{id}")
    public Long deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }

    @GetMapping("/orderItems/{id}")
    public List<OrderItemDto> getOrderItemsByOrderId(@PathVariable Long id) {
        return orderService.getOrderItemsByOrderId(id);
    }
}
