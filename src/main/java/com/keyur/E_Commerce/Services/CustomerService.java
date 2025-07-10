package com.keyur.E_Commerce.Services;


import com.keyur.E_Commerce.DTOs.CustomerDTO;
import com.keyur.E_Commerce.DTOs.CustomerUpdateDTO;
import com.keyur.E_Commerce.DTOs.SessionDTO;
import com.keyur.E_Commerce.Entities.Address;
import com.keyur.E_Commerce.Entities.CreditCard;
import com.keyur.E_Commerce.Entities.Customer;
import com.keyur.E_Commerce.Entities.Order;
import com.keyur.E_Commerce.ExceptionObjects.CustomerException;
import com.keyur.E_Commerce.ExceptionObjects.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    public Customer addCustomer(Customer customer) throws CustomerException;

    //public Customer getLoggedInCustomerDetails(String token) throws CustomerNotFoundException;

    public List<Customer> getAllCustomers() throws CustomerNotFoundException;

    public Customer updateCustomer(CustomerUpdateDTO customer) throws CustomerNotFoundException;

    public Customer updateCustomerMobileNoOrEmailId(CustomerUpdateDTO customerUpdateDTO) throws CustomerNotFoundException;

    public Customer updateCreditCardDetails(CreditCard card) throws CustomerException;

    public String updateCustomerPassword(CustomerDTO customerDTO) throws CustomerNotFoundException;

    public String deleteCustomer(CustomerDTO customerDTO) throws CustomerNotFoundException;

    public Customer updateAddress(Address address, String type) throws CustomerException;

    public List<Order> getCustomerOrders() throws CustomerException;
}
