package com.keyur.E_Commerce.Services;

import com.keyur.E_Commerce.DTOs.CartDTO;
import com.keyur.E_Commerce.Entities.Cart;
import com.keyur.E_Commerce.Entities.CartItem;
import com.keyur.E_Commerce.Entities.Customer;
import com.keyur.E_Commerce.ExceptionObjects.CartItemNotFound;
import com.keyur.E_Commerce.ExceptionObjects.ProductNotFound;
import com.keyur.E_Commerce.Repositories.CartDao;
import com.keyur.E_Commerce.Security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImp implements CartService {
    //fields
    CustomerServiceImp customerServiceImp;
    CartDao cartDao;
    CartItemServiceImp cartItemServiceImp;
    SecurityUtils securityUtils;

    //injecting dependencies using constructor injection
    CartServiceImp(CustomerServiceImp customerServiceImp, CartDao cartDao, CartItemServiceImp cartItemServiceImp, SecurityUtils securityUtils) {
        this.customerServiceImp = customerServiceImp;
        this.cartDao = cartDao;
        this.cartItemServiceImp = cartItemServiceImp;
        this.securityUtils = securityUtils;
    }
    @Override
    public Cart addProductToCart(CartDTO cartDto) throws CartItemNotFound {
        //validate the session and perform all required checks
        Customer customer = securityUtils.getCurrentCustomer();

        Cart customerCart = customer.getCustomerCart();
        List<CartItem> cartItems = customerCart.getCartItems();

        //we will have to check 2 cases here.
        //1) The item we are adding is not present in the cart
        //2) The item we are adding already exists in the cart and we need to update it's quantity

        //flag to check if the product we are adding, exists in the cart already or not
        boolean flag = false;

        for(CartItem cartItem : cartItems) {
            if(cartItem.getCartProduct().getProductId().equals(cartDto.getProductId())) {
                cartItem.setCartItemQuantity(cartItem.getCartItemQuantity() + cartDto.getQuantity());
                customerCart.setCartTotal(customerCart.getCartTotal() + (cartItem.getCartProduct().getPrice() * cartDto.getQuantity()));
                cartItem.setCart(customerCart);
                flag = true;
                break;
            }
        }

        CartItem itemToBeAdded = cartItemServiceImp.createItemForCart(cartDto);

        if(!flag) {
            customerCart.getCartItems().add(itemToBeAdded);
            customerCart.setCartTotal(customerCart.getCartTotal() + (itemToBeAdded.getCartProduct().getPrice() * cartDto.getQuantity()));
            itemToBeAdded.setCart(customerCart);
            itemToBeAdded.setCartItemQuantity(cartDto.getQuantity());
        }

        return cartDao.save(customerCart);
    }

    @Override
    public Cart getCartProducts() {
        Customer customer = securityUtils.getCurrentCustomer();

        Cart customerCart = customer.getCustomerCart();

        if(customerCart.getCartItems().isEmpty()) {
            throw new CartItemNotFound("No items in the cart!");
        }

        return customerCart;
    }

    @Override
    public Cart removeProductFromCart(CartDTO cartDto) throws ProductNotFound {
        //check session validity and perform all necessary checks
        Customer customer = securityUtils.getCurrentCustomer();

        Cart customerCart = customer.getCustomerCart();
        List<CartItem> cartItems = customerCart.getCartItems();

        //if the product that we are removing had quantity 1, then we will have to completely
        //remove it from the cart. If the quantity is more than 1, then we reduce its quantity by 1

        //check if there are any products in the cart from before. If not, we cant remove anything
        if(cartItems.isEmpty()) {
            throw new CartItemNotFound("No items in the cart!");
        }

        //flag to check if the product to be removed actually exists in the cart or not
        boolean flag = false;

        for(CartItem cartItem : cartItems) {
            if(cartItem.getCartProduct().getProductId().equals(cartDto.getProductId())) {
                cartItem.setCartItemQuantity(cartItem.getCartItemQuantity() - 1);
                customerCart.setCartTotal(customerCart.getCartTotal() - cartItem.getCartProduct().getPrice());

                if(cartItem.getCartItemQuantity() == 0) {
                    cartItems.remove(cartItem);
                    if(cartItems.size() == 0) {
                        cartDao.save(customerCart);
                        throw new CartItemNotFound("Cart is empty now");
                    }
                }
                cartDao.save(customerCart);
                flag = true;
            }
        }

        //if flag is still false, that means the item we are trying to remove does not exist in the cart
        if(!flag) {
            throw new CartItemNotFound("No item found matching in the cart!");
        }

        return customerCart;
    }

    @Override
    public Cart clearCart() {
        Customer existingCustomer = securityUtils.getCurrentCustomer();

        Cart customerCart = existingCustomer.getCustomerCart();

        if(customerCart.getCartItems().size() == 0) {
            throw new CartItemNotFound("Cart already empty");
        }

        customerCart.getCartItems().clear();

        customerCart.setCartTotal(0.0);

        return cartDao.save(customerCart);
    }
}
