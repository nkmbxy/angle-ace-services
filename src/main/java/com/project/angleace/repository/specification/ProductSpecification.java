package com.project.angleace.repository.specification;

import com.project.angleace.entity.Manufacturer;
import com.project.angleace.entity.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> hasNameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> hasTypeLike(String type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("type"), "%" + type + "%");
    }

    public static Specification<Product> hasManufacturerLike(String manufacturer) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Manufacturer> manufacturerJoin = root.join("manufacturer", JoinType.INNER);
            return criteriaBuilder.like(manufacturerJoin.get("name"), "%" + manufacturer + "%");
        };
    }

    public static Specification<Product> hasProductID(Integer productID) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), productID);
    }
}
