package com.example.demo.service;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.controller.Employee;
import com.example.demo.model.EmployeeForm;

public record EmployeeSpecification() {

	public static Specification<Employee> findByConditions(EmployeeForm form) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
