package com.keyur.E_Commerce.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class CartItem {
    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartItemId;

    @OneToOne
    @JsonIgnoreProperties(value={
            "productId",
            "seller",
            "quantity"
    })
    private Product cartProduct;

    private Integer cartItemQuantity;

    @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    @ManyToOne
    @JsonIgnore
    private Cart cart;


    //no args constructor
    public CartItem() {
    }


    //getters and setters
    public Integer getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Integer cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Product getCartProduct() {
        return cartProduct;
    }

    public void setCartProduct(Product cartProduct) {
        this.cartProduct = cartProduct;
    }

    public Integer getCartItemQuantity() {
        return cartItemQuantity;
    }

    public void setCartItemQuantity(Integer cartItemQuantity) {
        this.cartItemQuantity = cartItemQuantity;
    }

    public Cart getCart() { return cart;}

    public void setCart(Cart cart) { this.cart = cart;}


    //toString()
    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId=" + cartItemId +
                ", cartProduct=" + cartProduct +
                ", cartItemQuantity=" + cartItemQuantity +
                '}';
    }
}
