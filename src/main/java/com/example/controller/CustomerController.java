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
import com.example.model.Customer;
import com.example.model.User;
import com.example.repository.CustomerRepository;
import com.example.repository.UserRepository;
import com.example.service.CustomerService;


@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/bank")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/customers/add")
	public ResponseEntity<String> postCustomer(@RequestBody Customer customer) {
		
//		Fetch User info from employee input and save it in DB 
				User user = customer.getUser(); //I have username and password 
				//I will assign the role
				user.setRole("CUSTOMER");

				//Converting plain text password into encoded text
				String encodedPassword = passwordEncoder.encode(user.getPassword());
				//attach encoded password to user
				user.setPassword(encodedPassword);

				user  = userRepository.save(user);

				//Attach user object to employee
				customer.setUser(user);
		
		customerService.postCustomer(customer);
		return ResponseEntity.status(HttpStatus.OK).body("Customer added ...");
	}

	
	@GetMapping("/getall")
	public List<Customer> getAllCustomer() {
		List<Customer> list = customerService.getAllCustomer();
		return list;
	}

	@GetMapping("/one/{id}")
	public ResponseEntity<Object> getCustomerById(@PathVariable("id") int id) {
		Optional<Customer> optional = customerService.findById(id);

		if (optional == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID Given");

		Customer customer = optional.get();
		return ResponseEntity.status(HttpStatus.OK).body(customer);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customerDetails)
			throws ResourceNotFoundException {
		Customer customer = customerService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not exist with id: " + id));

		customer.setName(customerDetails.getName());
		customer.setAddress(customerDetails.getAddress());
		customer.setEmailId(customerDetails.getEmailId());
		customer.setNumber(customerDetails.getNumber());

		final Customer updatedCustomer = customerRepository.save(customer);
		return ResponseEntity.ok(updatedCustomer);
	}
	
	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable int id) throws ResourceNotFoundException {
		Customer customer = customerService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + id));

		customerService.deleteById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}