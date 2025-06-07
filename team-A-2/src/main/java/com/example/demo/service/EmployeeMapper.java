package com.example.demo.service;

import java.time.LocalDate;
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
	
	@Select({
	    "<script>",
	    "SELECT * FROM employeeInfo",
	    "WHERE 1=1",
	    "<if test='empId != null'>AND empId = #{empId}</if>",
	    "<if test='empName != null'>AND empName LIKE #{empName}</if>",
	    "<if test='ageFrom != null'>AND age &gt;= #{ageFrom}</if>",
	    "<if test='ageTo != null'>AND age &lt;= #{ageTo}</if>",
	    "<if test='startDateFrom != null'>AND startDate &gt;= #{startDateFrom}</if>",
	    "<if test='startDateTo != null'>AND startDate &lt;= #{startDateTo}</if>",
	    "<if test='endDateFrom != null'>AND endDate &gt;= #{endDateFrom}</if>",
	    "<if test='endDateTo != null'>AND endDate &lt;= #{endDateTo}</if>",
	    "</script>"
	})
	
	List<EmployeeInfo> searchByCondition(
	    @Param("empId") Integer empId,
	    @Param("empName") String empName,
	    @Param("ageFrom") Integer ageFrom,
	    @Param("ageTo") Integer ageTo,
	    @Param("startDateFrom") LocalDate startDateFrom,
	    @Param("startDateTo") LocalDate startDateTo,
	    @Param("endDateFrom") LocalDate endDateFrom,
	    @Param("endDateTo") LocalDate endDateTo
	);
	
}
