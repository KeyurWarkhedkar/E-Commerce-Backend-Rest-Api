package com.keyur.E_Commerce.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Customer {
    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customerId;

    @NotNull(message = "First Name cannot be NULL")
    @Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in first name")
    private String firstName;

    @NotNull(message = "Last Name cannot be NULL")
    @Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in last name")
    private String lastName;

    @NotNull(message = "Please enter the mobile Number")
    @Column(unique = true)
    @Pattern(regexp = "[6789]{1}[0-9]{9}", message = "Enter valid 10 digit mobile number")
    private String mobileNo;


    @NotNull(message = "Please enter the emaild id")
    @Column(unique = true)
    @Email
    private String emailId;

    @NotNull(message = "Please enter the password")
    //@Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters in length and can include A-Z, a-z, 0-9, or special characters !@#$%^&*_")
    private String password;

    private LocalDateTime createdOn;

    @Embedded
    private CreditCard creditCard;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_address_mapping",
            joinColumns = {
                @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "address_id", referencedColumnName = "addressId")
            }
    )
    @MapKeyColumn(name = "address_type")
    private Map<String, Address> address = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    private Cart customerCart;

    private String role;

    //no args constructor
    public Customer() {
    }


    //getters and setters
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Map<String, Address> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Address> address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Cart getCustomerCart() {
        return customerCart;
    }

    public void setCustomerCart(Cart customerCart) {
        this.customerCart = customerCart;
    }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }


    //toString()
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", createdOn=" + createdOn +
                ", creditCard=" + creditCard +
                ", address=" + address +
                ", orders=" + orders +
                ", customerCart=" + customerCart +
                '}';
    }
}
