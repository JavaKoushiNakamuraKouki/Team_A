package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.EmployeeForm;
import com.example.demo.model.EmployeeInfo;
import com.example.demo.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/update")

public class UpdateController {
	
	@Autowired
    private EmployeeService employeeService;
	
	@GetMapping("")
	public String showUpdatePage(@RequestParam(name = "empId", required = false) Integer empId, Model model) {
	    if (empId != null) {
	        EmployeeInfo employee = employeeService.findByEmpId(empId);
	        if (employee != null) {
	            EmployeeForm form = new EmployeeForm();
	            form.setEmpId(employee.getEmpId());
	            form.setEmpName(employee.getEmpName());
	            form.setAge(employee.getAge());
	            form.setPass(employee.getPass());
	            form.setStartDate(employee.getStartDate());
	            form.setEndDate(employee.getEndDate());
	            model.addAttribute("employeeForm", form);
	            return "update/updateInput";
	        } else {
	            model.addAttribute("error", "該当する社員が見つかりませんでした。");
	        }
	    }
	    if (!model.containsAttribute("employeeInfo")) {
	        model.addAttribute("employeeInfo", new EmployeeInfo());
	    }
	    System.out.println("遷移元リンクの社員ID: " + empId);
	    return "update/updateID";
	}
    
    @PostMapping("")
    public String getID(@RequestParam("empId") Integer empId, Model model) {
        EmployeeInfo employee = employeeService.findByEmpId(empId);
       
        
        if (employee == null) {
            model.addAttribute("error", "該当する社員が見つかりませんでした。");
            
            EmployeeInfo emptyEmployee = new EmployeeInfo();
            emptyEmployee.setEmpId(empId); // 入力値を保持
            model.addAttribute("employeeInfo", emptyEmployee);

            return "update/updateID";
        }
        
        EmployeeForm form = new EmployeeForm();
        form.setEmpId(employee.getEmpId());
        form.setEmpName(employee.getEmpName());
        form.setAge(employee.getAge());
        form.setPass(employee.getPass());
        form.setStartDate(employee.getStartDate());
        form.setEndDate(employee.getEndDate());
        
        model.addAttribute("employeeForm", form);
        return "update/updateInput";
    }
    
    @GetMapping("/input")
    public String showUpdateInputPage(
    	@RequestParam(name = "returnUrl", required = false) String returnUrl,
        Model model,
        HttpSession session) {
	//戻るボタン用
    if (returnUrl != null) {
        session.setAttribute("returnUrl", returnUrl);
    }
    	 return "update/updateInput";
    }
    
    
    @PostMapping("/confirm")
    		public String confirmUpdateInput(
    				@ModelAttribute("employeeForm") EmployeeForm updateForm,
    				Model model
    				) {
    	if (updateForm.getPass() != null &&
    			updateForm.getPass().equals(updateForm.getPassCon())) {

    		model.addAttribute("employeeForm", updateForm);
			return "update/updateConfirm";
    	}else {
            model.addAttribute("error", "パスワードが一致しません");
            model.addAttribute("employeeForm",updateForm);
            return "update/updateInput";
    	}
    }
    
    @GetMapping("/confirm")
    public String showUpdateConfirmPage(Model model) {
        if (!model.containsAttribute("employeeInfo")) {
            model.addAttribute("employeeInfo", new EmployeeInfo());
        }
        return "update/updateID";
    }

    
    @PostMapping("/back")
    public String goBackToInput(
            @ModelAttribute("employeeForm") EmployeeForm form,
            @RequestParam(value = "prevPage", required = false) String prevPage,
            Model model) {

        if ("updateID".equals(prevPage)) {
            EmployeeInfo employeeInfo = new EmployeeInfo();
            employeeInfo.setEmpId(form.getEmpId());
            model.addAttribute("employeeInfo", employeeInfo);
            return "update/updateID";
        }

        model.addAttribute("employeeForm", form);
        return "update/updateInput";
    }
  
	 
	 @PostMapping("/complete")
	    public String updateComplete(
	    		@ModelAttribute("employeeForm") EmployeeForm employeeForm
	    		) {
	        employeeService.updateEmployee(employeeForm); 
	        return "update/updateComplete"; 
		 }
    


    }
    

 

