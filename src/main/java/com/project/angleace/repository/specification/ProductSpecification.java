package com.project.angleace.repository.specification;

import com.project.angleace.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> hasNameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }
    public static Specification<Product> hasTypeLike(String type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("type"), "%" + type + "%");
    }
    public static Specification<Product> hasFactoryLike(String factory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("factory"), "%" + factory + "%");
    }
}
