package com.keyur.E_Commerce.ExceptionObjects;

public class ProductNotFound extends RuntimeException {
    public ProductNotFound() {

    }

    public ProductNotFound(String message) {
        super(message);
    }
}
