package com.keyur.E_Commerce.Specification;

import com.keyur.E_Commerce.DTOs.SearchFilterDTO;
import com.keyur.E_Commerce.Entities.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> getSpec(SearchFilterDTO req) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (req.getName() != null)
                predicates.add(cb.like(cb.lower(root.get("productName")), "%" + req.getName().toLowerCase() + "%"));

            if (req.getCategory() != null)
                predicates.add(cb.equal(root.get("category"), req.getCategory()));

            if (req.getMinPrice() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), req.getMinPrice()));

            if (req.getMaxPrice() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), req.getMaxPrice()));

            if (req.getSellerId() != null)
                predicates.add(cb.equal(root.get("seller").get("id"), req.getSellerId()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
