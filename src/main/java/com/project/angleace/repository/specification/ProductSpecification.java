package com.project.angleace.repository.specification;

import com.project.angleace.entity.Manufacturer;
import com.project.angleace.entity.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

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

    public static Specification<Product> hasBetweenStartPriceAndEndPrice(Double startPrice, Double endPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("sellPrice"), startPrice, endPrice);
    }

    public static Specification<Product> hasStartPrice(Double startPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("sellPrice"), startPrice);
    }

    public static Specification<Product> hasEndPrice(Double endPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("sellPrice"), endPrice);
    }

    public static Specification<Product> hasOrderBy(Sort sort) {
        return (root, query, criteriaBuilder) -> {
            if (sort != null) {
                List<Order> orders = new ArrayList<>();
                sort.forEach(order -> {
                    if (order.isAscending()) {
                        orders.add(criteriaBuilder.asc(root.get(order.getProperty())));
                    } else {
                        orders.add(criteriaBuilder.desc(root.get(order.getProperty())));
                    }
                });
                query.orderBy(orders);
            }
            return criteriaBuilder.and();
        };
    }

}
