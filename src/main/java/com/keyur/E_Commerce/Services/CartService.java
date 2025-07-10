package com.keyur.E_Commerce.Services;

import com.keyur.E_Commerce.DTOs.CartDTO;
import com.keyur.E_Commerce.Entities.Cart;
import com.keyur.E_Commerce.ExceptionObjects.CartItemNotFound;
import com.keyur.E_Commerce.ExceptionObjects.ProductNotFound;

public interface CartService {
    public Cart addProductToCart(CartDTO cartDto) throws CartItemNotFound;
    public Cart getCartProducts();
    public Cart removeProductFromCart(CartDTO cartDto) throws ProductNotFound;
    public Cart clearCart();
}
