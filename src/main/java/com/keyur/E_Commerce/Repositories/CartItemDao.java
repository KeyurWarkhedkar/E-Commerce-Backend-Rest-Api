package com.keyur.E_Commerce.Repositories;

import com.keyur.E_Commerce.Entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemDao extends JpaRepository<CartItem, Integer> {
}
