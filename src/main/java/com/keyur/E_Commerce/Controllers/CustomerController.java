package com.keyur.E_Commerce.Controllers;

import com.keyur.E_Commerce.DTOs.CustomerDTO;
import com.keyur.E_Commerce.DTOs.CustomerUpdateDTO;
import com.keyur.E_Commerce.DTOs.SessionDTO;
import com.keyur.E_Commerce.Entities.Address;
import com.keyur.E_Commerce.Entities.CreditCard;
import com.keyur.E_Commerce.Entities.Customer;
import com.keyur.E_Commerce.Entities.Order;
import com.keyur.E_Commerce.Security.SecurityUtils;
import com.keyur.E_Commerce.Services.CustomerServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    //fields
    CustomerServiceImp customerService;
    SecurityUtils securityUtils;

    //dependency injection
    CustomerController(CustomerServiceImp customerService, SecurityUtils securityUtils) {
        this.customerService = customerService;
        this.securityUtils = securityUtils;
    }

    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> getAllCustomersHandler(){
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.ACCEPTED);
    }


    // Handler to Get a customer details of currently logged in user - sends data as per token
    @GetMapping("/customer/current")
    public ResponseEntity<Customer> getLoggedInCustomerDetailsHandler(){
        return new ResponseEntity<>(securityUtils.getCurrentCustomer(), HttpStatus.ACCEPTED);
    }


    // Handler to Update a customer

    @PutMapping("/customer")
    public ResponseEntity<Customer> updateCustomerHandler(@Valid @RequestBody CustomerUpdateDTO customerUpdate){
        return new ResponseEntity<>(customerService.updateCustomer(customerUpdate), HttpStatus.ACCEPTED);
    }


    // Handler to update a customer email-id or mobile no
    @PutMapping("/customer/update/credentials")
    public ResponseEntity<Customer> updateCustomerMobileEmailHandler(@Valid @RequestBody CustomerUpdateDTO customerUpdate){
        return new ResponseEntity<>(customerService.updateCustomerMobileNoOrEmailId(customerUpdate), HttpStatus.ACCEPTED);
    }


    // Handler to update customer password
    @PutMapping("/customer/update/password")
    public ResponseEntity<String> updateCustomerPasswordHandler(@Valid @RequestBody CustomerDTO customerDto){
        return new ResponseEntity<>(customerService.updateCustomerPassword(customerDto), HttpStatus.ACCEPTED);
    }


    // Handler to Add or update new customer Address
    @PutMapping("/customer/update/address")
    public ResponseEntity<Customer> updateAddressHandler(@Valid @RequestBody Address address, @RequestParam("type") String type){
        return new ResponseEntity<>(customerService.updateAddress(address, type), HttpStatus.ACCEPTED);
    }


    // Handler to update Credit card details
    @PutMapping("/customer/update/card")
    public ResponseEntity<Customer> updateCreditCardHandler(@Valid @RequestBody CreditCard newCard){
        return new ResponseEntity<>(customerService.updateCreditCardDetails(newCard), HttpStatus.ACCEPTED);
    }


    // Handler to Remove a user address
    /*@DeleteMapping("/customer/delete/address")
    public ResponseEntity<Customer> deleteAddressHandler(@RequestParam("type") String type, @RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.deleteAddress(type, token), HttpStatus.ACCEPTED);
    }*/

    // Handler to delete customer
    @DeleteMapping("/customer")
    public ResponseEntity<String> deleteCustomerHandler(@Valid @RequestBody CustomerDTO customerDto){
        return new ResponseEntity<>(customerService.deleteCustomer(customerDto), HttpStatus.ACCEPTED);
    }



    @GetMapping("/customer/orders")
    public ResponseEntity<List<Order>> getCustomerOrdersHandler(){
        return new ResponseEntity<>(customerService.getCustomerOrders(), HttpStatus.ACCEPTED);
    }
}
