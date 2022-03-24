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

import com.springapp.entities.User;	
import com.springapp.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserRepository users;

	@RequestMapping
	public ResponseEntity<Page<User>> getAllUsers(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(users.findAll(pageable));
	}

	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody User User) {
		return ResponseEntity.status(HttpStatus.CREATED).body(users.save(User));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getUser(@PathVariable(value = "id") Long id) {
		Optional<User> UserO = users.findById(id);

		if (UserO.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(UserO.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		Optional<User> UserO = users.findById(id);
		if (!UserO.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		users.delete(UserO.get());
		return ResponseEntity.status(HttpStatus.OK).body("User Deleted.");
	}
}
