package com.springapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springapp.models.Product;
import com.springapp.repository.ProductRepository;

@Controller
@RequestMapping(path = "/stock")
public class ProductController {

	@Autowired
	ProductRepository stock;
	@RequestMapping
	public ModelAndView getAllProducts() {
			ModelAndView mv = new ModelAndView("/stock/stockProducts");
			List<Product> products = stock.findAll();
			mv.addObject("products", products);
			return mv;
	}
	
	@RequestMapping(path = "/find/{id}")
	public ResponseEntity<Optional<Product>> getById(@PathVariable Long id) {
		Optional<Product> product = stock.findById(id);
		if (product.isPresent()) {
			return new ResponseEntity<Optional<Product>>(product, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping("/add")
	public String addProduct(Product product) {
		stock.save(product);
		return "stock/formStock";
	}
	/* Adaptando para Mysql
	@RequestMapping(value = "/edit/{id}")
	public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product newProduct) {
		return stock.findById(id)
				.map(product -> {
					product.setName(newProduct.getName());
					product.setPrice(newProduct.getPrice());
					Product productUpdated = stock.save(product);
					return ResponseEntity.ok().body(productUpdated);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	@RequestMapping(path = "/delete/{id}")
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
	*/
}
