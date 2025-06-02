package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.EmployeeForm;
import com.example.demo.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/search")
public class SearchController {
	@Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public String showSearchPage(
    		@ModelAttribute("employeeForm") EmployeeForm employeeForm,
    		Model model,
    		@RequestParam(name = "returnUrl", required = false) String returnUrl,
	        HttpSession session) {
		//戻るボタン用
	    if (returnUrl != null) {
	        session.setAttribute("returnUrl", returnUrl);
	    }
        // 最初は空のフォームと空の検索結果を渡す
        model.addAttribute("employeeForm", employeeForm);
        model.addAttribute("resultList", null); 
        return "search/search";
    }
    
    @GetMapping("/clear")
    public String clearSearch(Model model, HttpSession session) {
        EmployeeForm employeeForm = new EmployeeForm(); // 空のフォーム
        model.addAttribute("employeeForm", employeeForm);
        model.addAttribute("resultList", null); // 結果もクリア
        // returnUrlが保存されていれば使う
        String returnUrl = (String) session.getAttribute("returnUrl");
        if (returnUrl != null) {
            return "redirect:" + returnUrl;
        }
        return "search/search"; // デフォルトは検索ページに戻る
    }
    
    @PostMapping("")
    public String Search(
            @Validated @ModelAttribute("employeeForm") EmployeeForm employeeForm,
            BindingResult bindingResult,
            Model model) {
    	System.out.println("▼ デバッグログ：フォーム値の確認 ▼");
    	System.out.println("検索条件 - empId: " + employeeForm.getEmpId());
    	System.out.println("empId: " + employeeForm.getEmpId());
    	System.out.println("empName: " + employeeForm.getEmpName());
        System.out.println(" 検索条件：");
        System.out.println("・社員ID: " + employeeForm.getEmpId());
        System.out.println("・社員名: " + employeeForm.getEmpName());
        System.out.println("・年齢From: " + employeeForm.getAgeFrom());
        System.out.println("・年齢To: " + employeeForm.getAgeTo());
        System.out.println("・開始日From: " + employeeForm.getStartDateFrom());
        System.out.println("・開始日To: " + employeeForm.getStartDateTo());
        System.out.println("・終了日From: " + employeeForm.getEndDateFrom());
        System.out.println("・終了日To: " + employeeForm.getEndDateTo());

        // 単項目チェック（BindingResultがエラーを持っていれば）
        if (bindingResult.hasErrors()) {
            model.addAttribute("resultList", null);
            return "search/search";
        }

        // 相関チェック（from > to）
        List<String> errors = validateForm(employeeForm);
        if (!errors.isEmpty()) {
            model.addAttribute("customErrors", errors);
            model.addAttribute("resultList", null);
            return "search/search";
        }

        // 検索処理
        List<com.example.demo.entity.Employee> resultList = employeeService.searchEmployees(employeeForm);
        if (resultList == null) {
            resultList = List.of();
        }

        model.addAttribute("employeeForm", employeeForm);
        model.addAttribute("resultList", resultList);
        model.addAttribute("resultCount", resultList.size());

        System.out.println("検索結果：" + resultList.size() + "件");

        return "search/search";
    }
	//Stringの空欄確認
	@SuppressWarnings("unused")
	private boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
	
	//検索結果不正の場合
	@SuppressWarnings("unused")
	private List<String> validateForm(EmployeeForm form) {
	    List<String> errors = new ArrayList<>();
	    if (form.getAgeFrom() != null && form.getAgeTo() != null &&
	        form.getAgeFrom() > form.getAgeTo()) {
	        errors.add("年齢の範囲が不正です（最小 > 最大）");
	    }

	    if (form.getStartDateFrom() != null && form.getStartDateTo() != null &&
	        form.getStartDateFrom().isAfter(form.getStartDateTo())) {
	        errors.add("開始日の範囲が不正です（From が To より後）");
	    }

	    if (form.getEndDateFrom() != null && form.getEndDateTo() != null &&
	        form.getEndDateFrom().isAfter(form.getEndDateTo())) {
	        errors.add("終了日の範囲が不正です（From が To より後）");
	    }
	    return errors;
	}
    
	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
}