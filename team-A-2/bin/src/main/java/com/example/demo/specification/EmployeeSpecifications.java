//package com.example.demo.specification;
//
//import java.time.LocalDate;
//
//import org.springframework.data.jpa.domain.Specification;
//
//import com.example.demo.model.EmployeeInfo;
//
//public class EmployeeSpecifications {
//    
//    public static Specification<EmployeeInfo> hasEmpName(String empName) {
//        return (root, query, builder) -> {
//            if (empName == null || empName.trim().isEmpty()) {
//                return builder.conjunction(); // 常に true
//            }
//            return builder.like(root.get("empName"), "%" + empName + "%");
//        };
//    }
//
//    public static Specification<EmployeeInfo> hasAgeBetween(Integer ageFrom, Integer ageTo) {
//        return (root, query, builder) -> {
//            if (ageFrom == null && ageTo == null) {
//                return builder.conjunction();
//            }
//            if (ageFrom != null && ageTo != null) {
//                return builder.between(root.get("age"), ageFrom, ageTo);
//            }
//            if (ageFrom != null) {
//                return builder.greaterThanOrEqualTo(root.get("age"), ageFrom);
//            }
//            return builder.lessThanOrEqualTo(root.get("age"), ageTo);
//        };
//    }
//
//    public static Specification<EmployeeInfo> hasStartDateBetween(LocalDate startDateFrom, LocalDate startDateTo) {
//        return (root, query, builder) -> {
//            if (startDateFrom == null && startDateTo == null) {
//                return builder.conjunction();
//            }
//            if (startDateFrom != null && startDateTo != null) {
//                return builder.between(root.get("startDate"), startDateFrom, startDateTo);
//            }
//            if (startDateFrom != null) {
//                return builder.greaterThanOrEqualTo(root.get("startDate"), startDateFrom);
//            }
//            return builder.lessThanOrEqualTo(root.get("startDate"), startDateTo);
//        };
//    }
//
//    public static Specification<EmployeeInfo> hasEndDateBetween(LocalDate endDateFrom, LocalDate endDateTo) {
//        return (root, query, builder) -> {
//            if (endDateFrom == null && endDateTo == null) {
//                return builder.conjunction();
//            }
//            if (endDateFrom != null && endDateTo != null) {
//                return builder.between(root.get("endDate"), endDateFrom, endDateTo);
//            }
//            if (endDateFrom != null) {
//                return builder.greaterThanOrEqualTo(root.get("endDate"), endDateFrom);
//            }
//            return builder.lessThanOrEqualTo(root.get("endDate"), endDateTo);
//        };
//    }
//}