package com.example.simpleecom.repository;

import com.example.simpleecom.entity.Product;
import com.example.simpleecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String username);
}
