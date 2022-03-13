package com.example.simpleecom.repository;

import com.example.simpleecom.entity.Order;
import com.example.simpleecom.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByOrderTrackingNumberContaining(String keyword, Pageable pageable);
}
