package com.keyur.E_Commerce.Repositories;

import com.keyur.E_Commerce.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
    Optional<Product> findBySeller_SellerIdAndProductName(Integer sellerId, String productName);
}
