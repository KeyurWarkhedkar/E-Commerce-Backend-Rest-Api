package com.keyur.E_Commerce.Security;

import com.keyur.E_Commerce.Entities.Customer;
import com.keyur.E_Commerce.Entities.Seller;
import com.keyur.E_Commerce.ExceptionObjects.CustomerNotFoundException;
import com.keyur.E_Commerce.ExceptionObjects.SellerNotFoundException;
import com.keyur.E_Commerce.Repositories.CustomerDao;
import com.keyur.E_Commerce.Repositories.SellerDao;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    //fields
    CustomerDao customerDao;
    SellerDao sellerDao;

    //dependency injection
    SecurityUtils(CustomerDao customerDao, SellerDao sellerDao) {
        this.customerDao = customerDao;
        this.sellerDao = sellerDao;
    }

    //method to check if a customer exists in the database or not
    public Customer getCurrentCustomer() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerDao.findByMobileNo(username)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    //method to check if a seller exists in the database or not
    public Seller getCurrentSeller() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return sellerDao.findByMobile(username)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found"));
    }
}
