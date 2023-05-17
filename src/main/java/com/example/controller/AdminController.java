package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Admin;
import com.example.model.User;

import com.example.repository.AdminRepository;
import com.example.repository.UserRepository;
import com.example.service.AdminService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/add")
	public ResponseEntity<String> postadmin(@RequestBody Admin admin) {
		
//		Fetch User info from employee input and save it in DB 
				User user = admin.getUser(); //I have username and password 
				//I will assign the role
				user.setRole("admin");

				//Converting plain text password into encoded text
				String encodedPassword = passwordEncoder.encode(user.getPassword());
				//attach encoded password to user
				user.setPassword(encodedPassword);

				user  = userRepository.save(user);

				//Attach user object to employee
				admin.setUser(user);
		
		adminService.postadmin(admin);
		return ResponseEntity.status(HttpStatus.OK).body("admin added ...");
	}

	
	@GetMapping("/getall")
	public List<Admin> getAlladmin() {
		List<Admin> list = adminService.getAlladmin();
		return list;
	}

	@GetMapping("/one/{id}")
	public ResponseEntity<Object> getadminById(@PathVariable("id") Long id) {
		Optional<Admin> optional = adminService.findById(id);

		if (optional == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID Given");

		Admin admin = optional.get();
		return ResponseEntity.status(HttpStatus.OK).body(admin);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Admin> updateadmin(@PathVariable Long id, @RequestBody Admin adminDetails)
			throws ResourceNotFoundException {
		Admin admin = adminService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("admin not exist with id: " + id));

		admin.setName(adminDetails.getName());
		admin.setAddress(adminDetails.getAddress());
		admin.setEmailId(adminDetails.getEmailId());
		admin.setNumber(adminDetails.getNumber());

		final Admin updatedadmin = adminRepository.save(admin);
		return ResponseEntity.ok(updatedadmin);
	}
	
	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteadmin(@PathVariable Long id) throws ResourceNotFoundException {
		Admin admin = adminService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("admin not found for this id :: " + id));

		adminService.deleteById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}