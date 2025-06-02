package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.EmployeeInfo;

 @Repository
	public interface EmployeeRepository extends JpaRepository<EmployeeInfo, Integer>{
 }



