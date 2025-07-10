package com.keyur.E_Commerce.ExceptionObjects;

public class ProductNotFoundException extends RuntimeException{

	public ProductNotFoundException() {
	}
	
	public ProductNotFoundException(String message){
		super(message);
	}
}
