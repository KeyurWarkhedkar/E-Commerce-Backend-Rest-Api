package com.keyur.E_Commerce.DTOs;

import java.util.HashMap;
import java.util.Map;

import com.keyur.E_Commerce.Entities.Address;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public class CustomerUpdateDTO {
	//fields
	@Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in first name")
	private String firstName;
	
	@Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in last name")
	private String lastName;
	
	@NotNull(message = "Please enter the mobile Number")
	@Pattern(regexp = "[6789]{1}[0-9]{9}", message = "Enter valid 10 digit mobile number")
	private String mobileNo;

	@Column(unique = true)
	@Email
	private String emailId;
	
	@Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters in length and can include A-Z, a-z, 0-9, or special characters !@#$%^&*_")
	private String password;
	
	private Map<String, Address> address = new HashMap<>();


	//no args constructor
	public CustomerUpdateDTO() {
	}


	//getters and setters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, Address> getAddress() {
		return address;
	}

	public void setAddress(Map<String, Address> address) {
		this.address = address;
	}
}
