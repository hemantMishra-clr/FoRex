package com.example.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Admin;
import com.example.model.User;



public interface AdminRepository extends JpaRepository<Admin, Long> {
	@Query("select u from Admin u where u.name=?1")
	Admin getAdminByAdminName(String name);
}
