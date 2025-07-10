package com.keyur.E_Commerce.Controllers;

import com.keyur.E_Commerce.DTOs.CartDTO;
import com.keyur.E_Commerce.Entities.Cart;
import com.keyur.E_Commerce.Services.CartServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    //fields
    CartServiceImp cartService;

    //dependency injection
    CartController(CartServiceImp cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "/customer/cart/add")
    public ResponseEntity<Cart> addProductToCartHandler(@RequestBody CartDTO cartDto){
        Cart cart = cartService.addProductToCart(cartDto);
        return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
    }

    @GetMapping(value = "/customer/cart")
    public ResponseEntity<Cart> getCartProductHandler(){
        return new ResponseEntity<>(cartService.getCartProducts(), HttpStatus.ACCEPTED);
    }


    @DeleteMapping(value = "/customer/cart")
    public ResponseEntity<Cart> removeProductFromCartHander(@RequestBody CartDTO cartdto){

        Cart cart = cartService.removeProductFromCart(cartdto);
        return new ResponseEntity<Cart>(cart,HttpStatus.OK);
    }


    @DeleteMapping(value = "/customer/cart/clear")
    public ResponseEntity<Cart> clearCartHandler(){
        return new ResponseEntity<>(cartService.clearCart(), HttpStatus.ACCEPTED);
    }
}
