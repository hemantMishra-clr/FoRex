package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;


@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository; 
	
	public void postCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	public Customer getCustomerById(int cid) {
		Optional<Customer> optional = customerRepository.findById(cid);
		//If customer exists, optional will not be null
		if(optional != null)
			return optional.get();//returning the department
		//If customer does not exist,optional will be null
		
		return null;
	}
	
	public List<Customer> getAllCustomer() {
		List<Customer> list = customerRepository.findAll();
		return list;
	}
	public void deleteById(int customerId) {
		customerRepository.deleteById(customerId);
	}

	public Optional<Customer> findById(int id) {
		Optional<Customer> optional = customerRepository.findById(id);
		return optional;
	}
}