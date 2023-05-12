package com.example.controller;



import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.repository.UserRepository;
import com.example.model.User;



@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("api/bank/user")
public class UserController {

	@Autowired
	private UserRepository userRepository; 

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody User user) {

		//Check username uniqueness
		User userDB = userRepository.getUserByUsername(user.getUsername());
		if(!(userDB == null)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already present");
		}

		//Converting plain text password into encoded text
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		//attach encoded password to user
		user.setPassword(encodedPassword);
		userRepository.save(user);
		return ResponseEntity.status(HttpStatus.OK).body("Signup Success");

	}

	@GetMapping("/login")
	public User login(Principal principal) { //it tells us currently loggedIn username
		//if u come to this line, it means that username and password given are valid
		User user  = userRepository.getUserByUsername(principal.getName());
		return user;
	}


	/* API for Security Verification */
	@GetMapping("/hello")
	public String getHello() {
		return "Hello";
	}
	@GetMapping("/private/hello")
	public String getAuthHello() {
		return "Auth Hello";
	}

	@GetMapping("private/role/hello")
	public String getPrivateRoleHello() {
		return "Private Role Hello";
	}

}