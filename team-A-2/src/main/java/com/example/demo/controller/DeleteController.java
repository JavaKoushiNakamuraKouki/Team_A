package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;

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
@RequestMapping("/delete")
public class DeleteController {

	
	@GetMapping("")
	public String showDelatePage(
	        @RequestParam(name = "returnUrl", required = false) String returnUrl,
	        Model model,
	        HttpSession session) {
		//戻るボタン用
	    if (returnUrl != null) {
	        session.setAttribute("returnUrl", returnUrl);
	    }
		
	    return "delete/delete"; 
	}
	
	@Autowired
    private EmployeeService employeeService;
	
	@PostMapping("/confirm")
	public String confirmDelete(
	        @RequestParam(name = "empIdList") List<Integer> empIdList,
	        @RequestParam(name = "returnUrl", required = false) String returnUrl,
	        Model model,
	        HttpSession session) {

	    EmployeeInfo loginUser = (EmployeeInfo) session.getAttribute("loginUser");
	    List<String> errors = new ArrayList<>();
	    List<EmployeeInfo> validEmployees = new ArrayList<>();
	    List<Integer> notFoundIds = new ArrayList<>();
	    System.out.println("returnUrl from form: " + returnUrl);
//	    String returnUrl = (String) session.getAttribute("returnUrl");
	    
//	    if(returnUrl == "/update/confirm") {
//	    	for (Integer empId : empIdList) {
//		        if (loginUser != null && empId.equals(loginUser.getEmpId())) {
//		            errors.add("ID " + empId + " は現在ログイン中です");
//		            model.addAttribute("errors", errors);
//	    	    // 元の employee 情報も戻す（削除→更新確認画面に戻るので）
//	    	    EmployeeInfo employee = employeeService.findByEmpId(empId);
//	    	    EmployeeForm form = new EmployeeForm();
//	    	    form.setEmpId(employee.getEmpId());
//	    	    form.setEmpName(employee.getEmpName());
//	    	    form.setAge(employee.getAge());
//	    	    form.setPass(employee.getPass());
//	    	    form.setStartDate(employee.getStartDate());
//	    	    form.setEndDate(employee.getEndDate());
//	    	    model.addAttribute("errors", errors);
//	    	    model.addAttribute("employeeForm", form);
//		        return "update/updateConfirm";
//		        }
//	    	}
//	    }
	    // 空欄の場合
	    if (empIdList == null || empIdList.isEmpty()) {
	        errors.add("削除対象のIDを入力してください");
	        model.addAttribute("errors", errors);
	        return "delete/delete"; // エラーがあれば入力画面に戻す
	    }

	    for (Integer empId : empIdList) {
	        // ログイン中のID確認
	        if (loginUser != null && empId.equals(loginUser.getEmpId())) {
	            errors.add("ID " + empId + " は現在ログイン中です");
	            model.addAttribute("errors", errors);
	            return "delete/delete"; // ログイン中のIDは削除できない
	        }

	        // DB内確認
	        EmployeeInfo employee = employeeService.findByEmpId(empId);
	        if (employee == null) {
	            notFoundIds.add(empId); // 存在しないID
	        } else {
	            validEmployees.add(employee); // 存在するID
	        }
	    }

	    // 全IDが不正の場合
	    if (validEmployees.isEmpty()) {
	        errors.add("入力したIDは存在しません");
	        model.addAttribute("errors", errors);
	        return "delete/delete"; // エラーがあれば入力画面に戻す
	    }
	    
	    // 削除対象がある場合
//	    System.out.println (returnUrl);
	    model.addAttribute("employeeList", validEmployees);
	    model.addAttribute("notFoundIds", notFoundIds);
	    model.addAttribute("returnUrl", returnUrl);
	    

	    return "delete/deleteConfirm"; 
	}
	


	@PostMapping("/back")
	public String backToDeleteInput(
	        @RequestParam(name = "empIdList", required = false) List<Integer> empIdList,
	        @ModelAttribute("employeeForm") EmployeeForm form,
            @RequestParam(value = "prevPage", required = false) String prevPage,
            @RequestParam(name = "returnUrl", required = false) String returnUrl,
	        Model model,
	        HttpSession session
	) {
		System.out.println("returnUrl from form: " + returnUrl);
		
		if ("/update/updateInput".equals(returnUrl) && empIdList != null && empIdList.size() == 1) {
	        Integer empId = empIdList.get(0);
	        EmployeeInfo employee = employeeService.findByEmpId(empId);
	        if (employee != null) {
            EmployeeForm employeeForm = new EmployeeForm();
            employeeForm.setEmpId(employee.getEmpId());
            employeeForm.setEmpName(employee.getEmpName());
            employeeForm.setAge(employee.getAge());
            employeeForm.setPass(employee.getPass());
            employeeForm.setStartDate(employee.getStartDate());
            employeeForm.setEndDate(employee.getEndDate());
            model.addAttribute("employeeForm", employeeForm);
            return "update/updateInput";
        } else {
            model.addAttribute("error", "対象の社員が存在しません。");
            return "update/updateID";
        }
	        }
	        if(empIdList != null && !empIdList.isEmpty()) {
	        model.addAttribute("empIdList", empIdList); 
	    }
	    return "delete/delete"; 
	}
	
	@PostMapping("/complete")
	public String deleteComplete(
	    @RequestParam(name = "empIdList") List<Integer> empIdList,
	    Model model
	) {
	    // 削除処理
	    for (Integer empId : empIdList) {
	        employeeService.deleteByEmpId(empId);
	    }

	    // 完了画面へ遷移
	    return "delete/deleteComplete";
	}	
	
	
	}
	


