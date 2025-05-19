package com.example.demo.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.EmployeeInfo;
import com.example.demo.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {
	
	@Autowired
    private EmployeeService loginService;
	
        @PostMapping("/login")
        public String processLogin(
        		@ModelAttribute("employeeInfo") EmployeeInfo employeeInfo,
        		Model model,
        		HttpSession session) {
      

            System.out.println("受信したID: " + employeeInfo.getEmpId());
            System.out.println("受信したパス: " + employeeInfo.getPass());
            
            Integer empId = employeeInfo.getEmpId();
            String pass = employeeInfo.getPass();
            String result = loginService.login(empId, pass);

            switch (result) {
           
                case "SUCCESS":
                    EmployeeInfo dbEmployee = loginService.findByEmpId(empId); 
                    if (dbEmployee != null) {
                        session.setAttribute("loginUser", dbEmployee); 
                    }
                   
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d HH:mm:ss");
                    String loginTime = LocalDateTime.now().format(formatter);
                    session.setAttribute("loginTime", loginTime);

                    model.addAttribute("empId", empId);
                    return "redirect:/main";

                case "ID_NOT_FOUND":
                    model.addAttribute("error", "IDが見つかりません");
                    return "login"; 

                case "WRONG_PASSWORD":
                    model.addAttribute("error", "パスワードが違います");
                    return "login"; 

                default:
                    model.addAttribute("error", "システムエラー");
                    return "login";
            }

        }
        
    	@GetMapping("/login")
        public String show (Model model) {
            model.addAttribute("employeeInfo", new EmployeeInfo());
            return "login";
    	}

        }
    

