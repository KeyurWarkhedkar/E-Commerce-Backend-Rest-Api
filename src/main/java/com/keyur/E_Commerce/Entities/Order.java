package com.keyur.E_Commerce.Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.keyur.E_Commerce.Enums.OrderStatusValues;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;


@Entity
@Table(name="orders")
public class Order {
	//fields
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;

	@PastOrPresent
	private LocalDate date;

	@NotNull
	@Enumerated(EnumType.STRING)
	private OrderStatusValues orderStatus;
	
	private Double total;
	
	private String cardNumber;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "customerId")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "address_id", referencedColumnName = "addressId")
	private Address address;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	List<OrderItems> orderItems;

	//no args constructor
	public Order() {
	}

	//getters and setters
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public OrderStatusValues getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatusValues orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<OrderItems> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItems> orderItems) {
		this.orderItems = orderItems;
	}


	//toString()
	@Override
	public String toString() {
		return "Order{" +
				"orderId=" + orderId +
				", date=" + date +
				", orderStatus=" + orderStatus +
				", total=" + total +
				", cardNumber='" + cardNumber + '\'' +
				", customer=" + customer +
				", orderItems=" + orderItems +
				", address=" + address +
				'}';
	}
}
