package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
    @GetMapping("/main")
    public String showMainPage(
    		HttpSession session) {
    	
    	if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
    	
        return "main"; 
        }
    
    @PostMapping("/back")
    public String goBack(HttpSession session) {
    	
    	if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
    	
        String returnUrl = (String) session.getAttribute("returnUrl");

        if (returnUrl != null) {
        	System.out.println (returnUrl);
            session.removeAttribute("returnUrl");
            return "redirect:" + returnUrl;
        }
        System.out.println (returnUrl);
        return "redirect:/main"; 
    }
    
}