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

import com.springapp.entities.Product;
import com.springapp.repositories.ProductRepository;

@RestController
@RequestMapping("/stock")
public class ProductController {

	@Autowired
	ProductRepository stock;

	@RequestMapping
	public ResponseEntity<Page<Product>> getAllProducts(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(stock.findAll(pageable));
	}

	@PostMapping
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		return ResponseEntity.status(HttpStatus.CREATED).body(stock.save(product));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getProduct(@PathVariable(value = "id") Long id) {
		Optional<Product> productO = stock.findById(id);

		if (productO.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(productO.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
		Optional<Product> productO = stock.findById(id);
		if (!productO.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		stock.delete(productO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Product Deleted.");
	}
}
