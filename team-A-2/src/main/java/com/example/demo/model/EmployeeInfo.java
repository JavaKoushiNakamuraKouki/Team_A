package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="employeeInfo")
public class EmployeeInfo {
	
	@Id
	 private Integer empId;
	
	 private String empName ;
	 
	 private Integer age;
	 
	 private String pass ;
	 
	 private LocalDate  startDate;
	 
	 private LocalDate  endDate;
	 
	 

	 public EmployeeInfo() {};	 
	 
	 public EmployeeInfo (int empId,String empName,int age,String pass,LocalDate startDate,LocalDate endDate) {
		 this.empId = empId ;
		 this.empName = empName ;
		 this.age = age ;
		 this.pass = pass ;
		 this.startDate = startDate ;
		 this.endDate = endDate ;
	 }
	 
	 

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public LocalDate  getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate localDate) {
		this.startDate = localDate;
	}

	public LocalDate  getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate  endDate) {
		this.endDate = endDate;
	}
	 

}
