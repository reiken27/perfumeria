package com.tienda.perfumeria.utils;

import org.springframework.data.jpa.domain.Specification;
import com.tienda.perfumeria.entities.Product;
import com.tienda.perfumeria.entities.Category;

public class ProductSpecification {

    public static Specification<Product> hasBrand(String brand) {
        return (root, query, criteriaBuilder) -> 
            (brand == null) ? null : criteriaBuilder.equal(root.get("brand"), brand);
    }

    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) -> 
            (name == null) ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> hasCategory(Category category) {
        return (root, query, criteriaBuilder) -> 
            (category == null) ? null : criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Product> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) return null;
            if (minPrice == null) return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            if (maxPrice == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
        };
    }
}
