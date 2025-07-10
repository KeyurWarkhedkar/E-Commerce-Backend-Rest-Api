package com.keyur.E_Commerce.Services;

import com.keyur.E_Commerce.DTOs.CartDTO;
import com.keyur.E_Commerce.Entities.CartItem;

public interface CartItemService {
    public CartItem createItemForCart(CartDTO cartdto);
}
