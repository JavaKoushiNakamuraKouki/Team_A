package com.example.demo.controller;

import java.time.format.DateTimeFormatter;
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

@RequestMapping("/search")
@Controller
public class SearchController {
	
	@Autowired
	 private EmployeeService service;
	
	
    @GetMapping("")
    public String showSearchPage(
    	    @ModelAttribute("employeeForm") EmployeeForm employeeForm,
    	    Model model,
    	    @RequestParam(name = "returnUrl", required = false) String returnUrl,
    	    HttpSession session) {
    	
    	if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }

    	    if (returnUrl != null) {
    	        session.setAttribute("returnUrl", returnUrl);
    	    }
    	   
    	    model.addAttribute("resultList", new ArrayList<>());
    	    
    	    if (!model.containsAttribute("deleteError")) {
    	        model.addAttribute("deleteError", null);
    	    }
    	    return "search/search";
    	}
    
    @PostMapping("")
    public String search(@ModelAttribute("employeeForm") EmployeeForm searchForm,
    		Model model){
    	List<EmployeeInfo> resultList;
    	
    	//空欄での入力だった場合
		if (isSearchFormEmpty(searchForm)) {
			resultList = service.selectAll();
			int numberOfResult = resultList.size();
			model.addAttribute("resultList",resultList);
			model.addAttribute("numberOfResult",numberOfResult);
		} else {	
		 List<String> validationError = validateForm(searchForm);
		 if(!validationError.isEmpty()){
			model.addAttribute("errorList",validationError);
			return  "search/search";
		 }
			
	        resultList = service.searchByCondition(searchForm);
		    }
		    if (resultList.isEmpty()) {
		    	
		        model.addAttribute("resultNULL", "該当する社員が見つかりませんでした。");
		    }
		    
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

		    for (EmployeeInfo emp : resultList) {
		        if (emp.getStartDate() != null) {
		            emp.setStartDateFormatted(emp.getStartDate().format(formatter));
		        }
		        if (emp.getEndDate() != null) {
		            emp.setEndDateFormatted(emp.getEndDate().format(formatter));
		        }
		    }

		    model.addAttribute("resultList", resultList);
		    model.addAttribute("numberOfResult", resultList.size());
		    return "search/search";
    }
    
    
    
    //全権空欄のTF確認
	private boolean isSearchFormEmpty(EmployeeForm form) {
		return (form.getEmpId() == null &&
				isEmpty(form.getEmpName()) &&
				form.getAgeFrom() == null &&
				form.getAgeTo() == null &&
				form.getStartDateFrom() == null &&
				form.getStartDateTo() == null &&
				form.getEndDateFrom() == null &&
				form.getEndDateTo() == null);
	}

	//Stringの空欄確認
	private boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
	
	//検索結果不正の場合
	private List<String> validateForm(EmployeeForm form) {
	    List<String> errors = new ArrayList<>();
	    if (form.getAgeFrom() != null && form.getAgeTo() != null &&
	        form.getAgeFrom() > form.getAgeTo()) {
	        errors.add("年齢の範囲が不正です。左側より大きい数字を入力してください。");
	    }

	    if (form.getStartDateFrom() != null && form.getStartDateTo() != null &&
	        form.getStartDateFrom().isAfter(form.getStartDateTo())) {
	        errors.add("開始日の範囲が不正です。左側より先の日付を入力してください");
	    }

	    if (form.getEndDateFrom() != null && form.getEndDateTo() != null &&
	        form.getEndDateFrom().isAfter(form.getEndDateTo())) {
	        errors.add("終了日の範囲が不正です。左側より先の日付を入力してください");
	    }
	    return errors;
	}
	
	//リセット
	@PostMapping("/reset")
	public String resetSearchForm(Model model,
	                              @RequestParam(name = "returnUrl", required = false) String returnUrl,
	                              HttpSession session) {
	    
	    // セッションチェック
	    if (session.getAttribute("loginUser") == null) {
	        return "redirect:/login";
	    }

	    // returnUrl の保存（不要なら省略可）
	    if (returnUrl != null) {
	        session.setAttribute("returnUrl", returnUrl);
	    }

	    // フォーム初期化（全項目クリア）
	    EmployeeForm emptyForm = new EmployeeForm();
	    model.addAttribute("employeeForm", emptyForm);

	    // 検索結果も空にする
	    model.addAttribute("resultList", new ArrayList<>());
	    model.addAttribute("numberOfResult", 0); 
	    model.addAttribute("errorList", new ArrayList<>()); 
	    model.addAttribute("resultNULL", null); 

	    return "search/search";
	}
	
}

