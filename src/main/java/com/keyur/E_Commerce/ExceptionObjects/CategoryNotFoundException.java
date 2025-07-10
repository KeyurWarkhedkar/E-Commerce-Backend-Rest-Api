package com.keyur.E_Commerce.ExceptionObjects;

public class CategoryNotFoundException extends RuntimeException{

	public CategoryNotFoundException() {

	}
	
	
	public CategoryNotFoundException(String message) {
		super(message);
	}
	
}
