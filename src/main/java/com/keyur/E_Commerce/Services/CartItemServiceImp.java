package com.keyur.E_Commerce.Services;

import com.keyur.E_Commerce.DTOs.CartDTO;
import com.keyur.E_Commerce.Entities.CartItem;
import com.keyur.E_Commerce.Entities.Product;
import com.keyur.E_Commerce.Enums.ProductStatus;
import com.keyur.E_Commerce.ExceptionObjects.ProductNotFoundException;
import com.keyur.E_Commerce.Repositories.ProductDao;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImp implements CartItemService{
    //fields
    ProductDao productDao;

    //injecting dependencies using constructor injection
    CartItemServiceImp(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public CartItem createItemForCart(CartDTO cartdto) {
        Product existingProduct = productDao.findById(cartdto.getProductId()).orElseThrow( () -> new ProductNotFoundException("Product Not found"));

        if(existingProduct.getStatus().equals(ProductStatus.OUTOFSTOCK) || existingProduct.getQuantity() == 0) {
            throw new ProductNotFoundException("Product OUT OF STOCK");
        }

        CartItem newItem = new CartItem();

        newItem.setCartItemQuantity(1);

        newItem.setCartProduct(existingProduct);

        return newItem;
    }
}
