package com.springapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.entities.Order;
import com.springapp.repositories.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderRepository orders;

	@RequestMapping
	public ResponseEntity<Page<Order>> getAllOrders(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(orders.findAll(pageable));
	}

	@PostMapping
	public ResponseEntity<Order> saveOrder(@RequestBody Order Order) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orders.save(Order));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getOrder(@PathVariable(value = "id") Long id) {
		Optional<Order> OrderO = orders.findById(id);

		if (OrderO.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(OrderO.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteOrder(@PathVariable Long id) {
		Optional<Order> OrderO = orders.findById(id);
		if (!OrderO.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
		}
		orders.delete(OrderO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Order Deleted.");
	}
}
