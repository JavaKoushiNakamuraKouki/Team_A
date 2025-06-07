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
import com.example.demo.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
	
	@GetMapping("")
	public String showRegistrationPage(
			@RequestParam(name = "returnUrl", required = false) String returnUrl,
            Model model,
            HttpSession session) {
		
		if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
    	
		if (returnUrl != null) {
			session.setAttribute("returnUrl", returnUrl); 
			}
		model.addAttribute("employeeForm", new EmployeeForm());
		return "registration/registration";
		}
	
	 @PostMapping("/confirm") 
	    public String registrationConfirm(
	    		@ModelAttribute("employeeForm") EmployeeForm registrationForm,
	    		Model model
	    		) {
			if (registrationForm.getPass() != null &&
				    registrationForm.getPass().equals(registrationForm.getPassCon())) {
					
					model.addAttribute("employeeForm", registrationForm);
					return "registration/registrationConfirm";
				}
		 	
		 	model.addAttribute("error","パスワードが一致しません");
		 	return "/registration/registration"; 
	 }
	 
	 @GetMapping("/confirm")
	 public String registrationConfirmPage(
			 Model model,
			 HttpSession session) {
	    	
		 model.addAttribute("employeeForm", new EmployeeForm());
	     return "registration/registrationConfirm";
	 }
	 
	 @Autowired
	 private EmployeeService registrationService;
	 
	 @PostMapping("/complete")
	 public String registrationComplete(
			 @ModelAttribute("employeeForm") EmployeeForm registrationForm,
			 Model model
			 ) {
		 Integer empId = registrationService.registerEmployee(registrationForm);
		 model.addAttribute("registration","社員ID："+ empId);
		 return "/registration/registrationComplete";
		 }
	 
	 @PostMapping("/back")
	 public String goBackToInput(
			 @ModelAttribute("employeeForm") EmployeeForm form,
			 Model model,
			 HttpSession session) {
			
			if (session.getAttribute("loginUser") == null) {
	            return "redirect:/login";
	        }
		    model.addAttribute("employeeForm", form);
		    return "registration/registration"; 
		}
	 
}
