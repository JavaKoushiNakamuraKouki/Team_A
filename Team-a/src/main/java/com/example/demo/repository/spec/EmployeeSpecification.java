package com.example.demo.repository.spec;

import com.example.demo.entity.Employee;
import com.example.demo.model.EmployeeForm;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<Employee> searchByConditions(EmployeeForm form) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 社員ID
            if (form.getEmpId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("empId"), form.getEmpId()));
            }

            // 社員名（前後の空白を削除して検索）
            if (form.getEmpName() != null) {
            String empName = form.getEmpName();
            if (empName != null && !empName.trim().isEmpty()) {empName = empName.trim();
            predicates.add(criteriaBuilder.like(root.get("empName"), "%" + empName + "%"));
              }
            }

            // 年齢範囲
            if (form.getAgeFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("age"), form.getAgeFrom()));
            }
            if (form.getAgeTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("age"), form.getAgeTo()));
            }

            // 開始日範囲
            if (form.getStartDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), form.getStartDateFrom()));
            }
            if (form.getStartDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), form.getStartDateTo()));
            }

            // 終了日範囲
            if (form.getEndDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), form.getEndDateFrom()));
            }
            if (form.getEndDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), form.getEndDateTo()));
             }

          
            if (predicates.isEmpty()) {
                return null;
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
