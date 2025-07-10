package com.keyur.E_Commerce.DTOs;

import com.keyur.E_Commerce.Entities.CreditCard;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.*;


public class OrderDTO {
	//fields
	@NotNull
	@Embedded
	private CreditCard creditCard;

	@NotNull
	private String addressType;

	//no args constructors
	public OrderDTO() {
	}


	//getters and setters
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
}
