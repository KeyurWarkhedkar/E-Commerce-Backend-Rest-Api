package com.keyur.E_Commerce.Repositories;

import com.keyur.E_Commerce.Entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerDao extends JpaRepository<Seller, Integer> {
    Optional<Seller> findByMobile(String mobile);
}
