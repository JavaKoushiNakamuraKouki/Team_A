package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.EmployeeForm;
import com.example.demo.model.EmployeeInfo;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.spec.EmployeeSpecification;



@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeMapper mapper;
	
    public List<com.example.demo.entity.Employee> searchEmployees(EmployeeForm form) {
        return employeeRepository.findAll(EmployeeSpecification.searchByConditions(form));
    }
	
	//ID検索
	public EmployeeInfo findByEmpId(int empId) {
	    return mapper.findByEmpId(empId);
	}
	
	public String login(int empId, String pass) {
		EmployeeInfo employee = mapper.findByEmpId(empId);

        if (employee == null) {
            return "ID_NOT_FOUND";
        }

        if (!employee.getPass().equals(pass)) {
            return "WRONG_PASSWORD";
        }

        return "SUCCESS";
    }
	
	//empID分岐
	public String findID (int empId) {
		EmployeeInfo employee = mapper.findByEmpId(empId);
        if (employee == null) {
            return "ID_NOT_FOUND";
        }
        return "SUCCESS";
        }
	
	
	//登録処理
    public Integer registerEmployee(EmployeeForm form) {
        EmployeeInfo entity = new EmployeeInfo();
        entity.setEmpName(form.getEmpName());
        entity.setAge(form.getAge());
        entity.setPass(form.getPass()); 
        entity.setStartDate(LocalDate.now());
        mapper.insert(entity);
        return entity.getEmpId(); 
    }
    
    //更新処理
    public void updateEmployee(EmployeeForm form) {
        EmployeeInfo entity = new EmployeeInfo();
        entity.setEmpId(form.getEmpId());
        entity.setEmpName(form.getEmpName());
        entity.setAge(form.getAge());
        entity.setPass(form.getPass());
        entity.setStartDate(form.getStartDate());
        entity.setEndDate(form.getEndDate());
        
       mapper.update(entity);
    	
    }
    
    //削除処理
    public void deleteByEmpId(int empId) {
        mapper.deleteByEmpId(empId);
    }
    
    //検索処理
  public List<EmployeeInfo> selectAll(){
	  return mapper.selectAll();
  }

//  ↓小野追加↓
  @Autowired
  private EmployeeRepository employeeRepository;

//  public List<Employee> searchEmployees1(EmployeeForm form) {
//      // Specificationを使って検索
//      return employeeRepository.findAll(EmployeeSpecification.findByConditions(form));
//  }

}

