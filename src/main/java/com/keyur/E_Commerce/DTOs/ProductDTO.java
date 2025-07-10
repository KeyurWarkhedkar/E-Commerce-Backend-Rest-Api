package com.keyur.E_Commerce.DTOs;




public class ProductDTO {
	//fields
	private String prodName;
	private String manufacturer;
	private Double price;
	private Integer quantity;
	
	//no args constructor
	public ProductDTO() {
	}


	//getters and setters
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
