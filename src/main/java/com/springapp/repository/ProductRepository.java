package com.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springapp.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
