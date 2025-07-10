package com.keyur.E_Commerce.Services;

import com.keyur.E_Commerce.DTOs.CustomerDTO;
import com.keyur.E_Commerce.DTOs.SellerDTO;
import com.keyur.E_Commerce.DTOs.SessionDTO;
import com.keyur.E_Commerce.Entities.Customer;
import com.keyur.E_Commerce.Entities.UserSession;
import org.springframework.http.ResponseEntity;

public interface LoginLogoutSevice {
    public String loginCustomer(CustomerDTO customer);

    public String logoutCustomer();

    public boolean checkSessionValidity(UserSession currentSession);

    public String loginSeller(SellerDTO seller);

    public String logoutSeller(SessionDTO session);
}
