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

import com.example.model.Admin;
import com.example.model.User;
import com.example.repository.AdminRepository;
import com.example.repository.UserRepository;



@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("api/bank/user")
public class UserController {

	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private AdminRepository adminRepository;

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
//user login
	@GetMapping("/login")
	public ResponseEntity<Object> login(Principal principal) { //it tells us currently loggedIn username
		//if u come to this line, it means that username and password given are valid
		User user  = userRepository.getUserByUsername(principal.getName());
		if(user.getRole().matches("CUSTOMER")) {
			return ResponseEntity.status(HttpStatus.OK).body(user);}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signup failed");
	}

//admin login
	@GetMapping("admin/login")
	
	public ResponseEntity<Object> adminLogin(Principal principal) { //it tells us currently loggedIn username
		//if u come to this line, it means that username and password given are valid
		User user  = userRepository.getUserByUsername(principal.getName());
		if(user.getRole().matches("admin")) {
		return ResponseEntity.status(HttpStatus.OK).body(user);}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signup failed");
		}
	
	
	
//	public Admin adminLogin(Principal principal) { //it tells us currently loggedIn username
//		//if u come to this line, it means that username and password given are valid
//		
//		Admin admin  = adminRepository.getAdminByAdminName(principal.getName());
//		return admin;
//	}
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