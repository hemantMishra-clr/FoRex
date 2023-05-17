package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Admin;
import com.example.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository; 
	
	public void postadmin(Admin admin) {
		adminRepository.save(admin);
	}

	public Admin getadminById(Long cid) {
		Optional<Admin> optional = adminRepository.findById(cid);
		//If admin exists, optional will not be null
		if(optional != null)
			return optional.get();//returning the department
		//If admin does not exist,optional will be null
		
		return null;
	}
	
	public List<Admin> getAlladmin() {
		List<Admin> list = adminRepository.findAll();
		return list;
	}
	public void deleteById(Long adminId) {
		adminRepository.deleteById(adminId);
	}

	public Optional<Admin> findById(Long id) {
		Optional<Admin> optional = adminRepository.findById(id);
		return optional;
	}
}
