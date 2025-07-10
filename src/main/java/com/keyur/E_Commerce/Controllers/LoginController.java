package com.keyur.E_Commerce.Controllers;

import com.keyur.E_Commerce.DTOs.CustomerDTO;
import com.keyur.E_Commerce.DTOs.SellerDTO;
import com.keyur.E_Commerce.DTOs.SessionDTO;
import com.keyur.E_Commerce.Entities.Customer;
import com.keyur.E_Commerce.Entities.Seller;
import com.keyur.E_Commerce.Entities.UserSession;
import com.keyur.E_Commerce.Services.CustomerServiceImp;
import com.keyur.E_Commerce.Services.LoginLogoutServiceImp;
import com.keyur.E_Commerce.Services.SellerServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    //defining fields for injection
    LoginLogoutServiceImp loginService;
    CustomerServiceImp customerService;
    SellerServiceImp sellerService;

    //injecting values into fields using constructor injection
    public LoginController(LoginLogoutServiceImp loginService, CustomerServiceImp customerService, SellerServiceImp sellerService) {
        this.loginService = loginService;
        this.customerService = customerService;
        this.sellerService = sellerService;
    }

    // Handler to register a new customer

    @PostMapping(value = "/customer/register", consumes = "application/json")
    public ResponseEntity<Customer> registerAccountHandler(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.CREATED);
    }

    // Handler to login a user

    @PostMapping(value = "/customer/login", consumes = "application/json")
    public ResponseEntity<String> loginCustomerHandler(@Valid @RequestBody CustomerDTO customerdto){
        return new ResponseEntity<>(loginService.loginCustomer(customerdto), HttpStatus.ACCEPTED);
    }


    // Handler to logout a user

    @PostMapping(value = "/customer/logout", consumes = "application/json")
    public ResponseEntity<String> logoutCustomerHandler(@RequestBody SessionDTO sessionToken){
        return new ResponseEntity<>(loginService.logoutCustomer(), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/seller/register", consumes = "application/json")
    public ResponseEntity<Seller> registerSellerAccountHandler(@Valid @RequestBody Seller seller) {
        return new ResponseEntity<>(sellerService.addSeller(seller), HttpStatus.CREATED);
    }


    // Handler to login a user

    @PostMapping(value = "/seller/login", consumes = "application/json")
    public ResponseEntity<String> loginSellerHandler(@Valid @RequestBody SellerDTO seller){
        return new ResponseEntity<>(loginService.loginSeller(seller), HttpStatus.ACCEPTED);
    }


    // Handler to logout a user

    @PostMapping(value = "/seller/logout", consumes = "application/json")
    public ResponseEntity<String> logoutSellerHandler(@RequestBody SessionDTO sessionToken){
        return new ResponseEntity<>(loginService.logoutSeller(sessionToken), HttpStatus.ACCEPTED);
    }
}
