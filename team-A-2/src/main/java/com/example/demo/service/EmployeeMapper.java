package com.example.demo.service;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.model.EmployeeInfo;

@Mapper
public interface EmployeeMapper {
	

    @Select("SELECT * FROM employeeInfo WHERE empId = #{empId}")
    EmployeeInfo findByEmpId(@Param("empId") int empId);
	
	
	@Select ( "SELECT * FROM employeeInfo WHERE empId = #{empId}")
	List<EmployeeInfo> selectById(int empId);
	
	@Insert("INSERT INTO employeeInfo (empName, age, pass, startDate, endDate) " +
	        "VALUES (#{empName}, #{age}, #{pass}, #{startDate}, NULL)")
	@Options(useGeneratedKeys = true, keyProperty = "empId")
	void insert(EmployeeInfo registrationEmployee);
	
	@Update("UPDATE employeeInfo SET empName = #{empName}, age = #{age}, pass = #{pass}, startDate = #{startDate}, endDate = #{endDate} WHERE empId = #{empId}")
	void update(EmployeeInfo updateEmployee);
	
	@Delete("DELETE FROM employeeInfo WHERE empId = #{empId}")
	void deleteByEmpId(int empId);
	
	@Select ("SELECT * FROM employeeInfo")
	List<EmployeeInfo> selectAll();
	
}
