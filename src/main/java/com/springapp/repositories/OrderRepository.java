package com.springapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springapp.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
