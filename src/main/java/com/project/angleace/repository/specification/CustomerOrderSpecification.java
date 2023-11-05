package com.project.angleace.repository.specification;

import com.project.angleace.entity.CustomerOrder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class CustomerOrderSpecification {
    public static Specification<CustomerOrder> hasStartDate(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
    }

    public static Specification<CustomerOrder> hasEndDate(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
    }

    public static Specification<CustomerOrder> hasStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate startPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            Predicate endPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
            return criteriaBuilder.and(startPredicate, endPredicate);
        };
    }
}
