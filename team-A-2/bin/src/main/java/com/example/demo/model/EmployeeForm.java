package com.example.demo.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class EmployeeForm {
	
	private Integer empId;  ;
	private String empName;
	private Integer age;
	private String pass;
	private String passCon; // 登録・更新で使用
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	// 検索で使用
	private Integer ageFrom;
	private Integer ageTo;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDateFrom;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDateTo;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDateFrom;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDateTo;
	
	
	// 検索結果　表示用
	private String startDateFormatted;

	private String endDateFormatted;
	


	// --- Getter / Setter ---

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

	public String getPassCon() {
		return passCon;
	}

	public void setPassCon(String passCon) {
		this.passCon = passCon;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getAgeFrom() {
		return ageFrom;
	}

	public void setAgeFrom(Integer ageFrom) {
		this.ageFrom = ageFrom;
	}

	public Integer getAgeTo() {
		return ageTo;
	}

	public void setAgeTo(Integer ageTo) {
		this.ageTo = ageTo;
	}

	public LocalDate getStartDateFrom() {
		return startDateFrom;
	}

	public void setStartDateFrom(LocalDate startDateFrom) {
		this.startDateFrom = startDateFrom;
	}

	public LocalDate getStartDateTo() {
		return startDateTo;
	}

	public void setStartDateTo(LocalDate startDateTo) {
		this.startDateTo = startDateTo;
	}

	public LocalDate getEndDateFrom() {
		return endDateFrom;
	}

	public void setEndDateFrom(LocalDate endDateFrom) {
		this.endDateFrom = endDateFrom;
	}

	public LocalDate getEndDateTo() {
		return endDateTo;
	}

	public void setEndDateTo(LocalDate endDateTo) {
		this.endDateTo = endDateTo;
	}
	
	public List<EmployeeInfo> getResultList() {
		return resultList;
	}

	public void setResultList(List<EmployeeInfo> resultList) {
		this.resultList = resultList;
	}

	private List<EmployeeInfo> resultList;
	
	public String getStartDateFormatted() {
	    return startDateFormatted;
	}

	public void setStartDateFormatted(String startDateFormatted) {
	    this.startDateFormatted = startDateFormatted;
	}

	public String getEndDateFormatted() {
	    return endDateFormatted;
	}

	public void setEndDateFormatted(String endDateFormatted) {
	    this.endDateFormatted = endDateFormatted;
	}
	

}
