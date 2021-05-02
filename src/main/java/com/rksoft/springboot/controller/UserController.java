package com.rksoft.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rksoft.springboot.entity.User;
import com.rksoft.springboot.exception.ResourceNotFoundException;
import com.rksoft.springboot.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository userRepositoty;

	// get all user
	@GetMapping()
	public List<User> getAllUsers() {
		return this.userRepositoty.findAll();

	}

	// get user by id
	@GetMapping("/{id}")
	public User getUserById(@PathVariable(value = "id") long userId) {
		return this.userRepositoty.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with user id " + userId));
	}

	// create user/save user
	@PostMapping
	public User createUser(@RequestBody User user) {
		return this.userRepositoty.save(user);
	}

	// update user
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user, @PathVariable(value = "id") long userId) {
		User existingUser = this.userRepositoty.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with user id" + userId));
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		return this.userRepositoty.save(existingUser);
	}

	// delete user by Id

	@DeleteMapping("{id}")
	public ResponseEntity<User> deleteUser(@PathVariable(value = "id") long userId) {
		User existingUser = this.userRepositoty.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with user id" + userId));
		this.userRepositoty.delete(existingUser);
		return ResponseEntity.ok().build();
	}

}
