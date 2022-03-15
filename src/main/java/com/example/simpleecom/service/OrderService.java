package com.example.simpleecom.service;

import com.example.simpleecom.dto.OrderDto;
import com.example.simpleecom.dto.OrderItemDto;
import com.example.simpleecom.entity.Customer;
import com.example.simpleecom.entity.Order;
import com.example.simpleecom.entity.OrderItem;
import com.example.simpleecom.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Page<OrderDto> getAllOrders(String keyword, Pageable pageable) {

        Page<Order> pageOrders;

        if (keyword == null) {
            pageOrders = orderRepository.findAll(pageable);
        }
        else {
            pageOrders = orderRepository.findByOrderTrackingNumberContaining(keyword, pageable);
        }
        Page<OrderDto> pageOrdersDto = pageOrders.map(this::orderEntityToDto);

        return pageOrdersDto;
    }

    public OrderDto getOrderById(Long orderId) {
        return orderEntityToDto(orderRepository.findById(orderId).orElseThrow());
    }

    public Long updateOrder(Long id, OrderDto orderDto) {
        Order tempOrder = orderRepository.findById(id).orElseThrow();
        tempOrder.setAddress(orderDto.getAddress());
        tempOrder.setCountry(orderDto.getCountry());
        tempOrder.setCity(orderDto.getCity());
        tempOrder.setState(orderDto.getState());
        tempOrder.setZipCode(orderDto.getZipCode());

        Customer newCustomer = new Customer();
        newCustomer.setId(tempOrder.getCustomer().getId());
        newCustomer.setOrders(tempOrder.getCustomer().getOrders());
        newCustomer.setFirstName(orderDto.getCustomerFirstName());
        newCustomer.setLastName(orderDto.getCustomerLastName());
        newCustomer.setEmail(orderDto.getCustomerEmail());
        newCustomer.setPhoneNumber(orderDto.getCustomerPhoneNumber());
        tempOrder.setCustomer(newCustomer);

        Order updateOrder = orderRepository.save(tempOrder);
        return updateOrder.getId();

    }

    public Long deleteOrder(Long orderId) {
        Order tempOrder = orderRepository.findById(orderId).orElseThrow();
        orderRepository.delete(tempOrder);
        return orderId;
    }

    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        List<OrderItemDto> tempOrderItemDtoList = orderEntityToDto(orderRepository.findById(orderId).orElseThrow()).getOrderItemList();
        return tempOrderItemDtoList;
    }

    public OrderItemDto orderItemEntityToDto(OrderItem orderItem) {
        OrderItemDto tempOrderItemDto = new OrderItemDto();
        tempOrderItemDto.setName(orderItem.getName());
        tempOrderItemDto.setUnitPrice(orderItem.getUnitPrice());
        tempOrderItemDto.setQuantity(orderItem.getQuantity());
        tempOrderItemDto.setProductId(orderItem.getProductId());
        return tempOrderItemDto;
    }

    public OrderDto orderEntityToDto(Order order) {
        OrderDto tempOrderDto = new OrderDto();
        tempOrderDto.setId(order.getId());
        tempOrderDto.setOrderTrackingNumber(order.getOrderTrackingNumber());
        tempOrderDto.setTotalQuantity(order.getTotalQuantity());
        tempOrderDto.setTotalPrice(order.getTotalPrice());
        tempOrderDto.setAddress(order.getAddress());
        tempOrderDto.setCity(order.getCity());
        tempOrderDto.setState(order.getState());
        tempOrderDto.setCountry(order.getCountry());
        tempOrderDto.setZipCode(order.getZipCode());
        tempOrderDto.setNote(order.getNote());
        tempOrderDto.setDateCreated(order.getDateCreated());
        tempOrderDto.setCustomerFirstName(order.getCustomer().getFirstName());
        tempOrderDto.setCustomerLastName(order.getCustomer().getLastName());
        tempOrderDto.setCustomerEmail(order.getCustomer().getEmail());
        tempOrderDto.setCustomerPhoneNumber(order.getCustomer().getPhoneNumber());

        List<OrderItemDto> orderItemDtoList = order.getOrderItems().stream().map(this::orderItemEntityToDto).collect(Collectors.toList());
        tempOrderDto.setOrderItemList(orderItemDtoList);

        return tempOrderDto;
    }


}
