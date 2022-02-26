package com.springapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.models.Product;
import com.springapp.repository.ProductRepository;

@RestController
@RequestMapping(path = "/stock")
public class ProductController {
	
	@Autowired
	ProductRepository stock;
	
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		try {
			List<Product> list = stock.findAll();
			
			if (list.isEmpty() || list.size() == 0) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Optional<Product>> getById(@PathVariable Long id) {
		Optional<Product> product = stock.findById(id);
		if (product.isPresent()) {
			return new ResponseEntity<Optional<Product>>(product, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<Product> saveCustomer(@RequestBody Product product) {
		try {
			return new ResponseEntity<>(stock.save(product), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product newProduct) {
		return stock.findById(id)
				.map(product -> {
					product.setName(newProduct.getName());
					product.setPrice(newProduct.getPrice());
					Product productUpdated = stock.save(product);
					return ResponseEntity.ok().body(productUpdated);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
		try {
			Optional<Product> product = stock.findById(id);
			if (product.isPresent()) {
				stock.delete(product.get());
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
