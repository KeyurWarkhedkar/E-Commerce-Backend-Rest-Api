package com.keyur.E_Commerce.Services;

import com.keyur.E_Commerce.DTOs.CustomerDTO;
import com.keyur.E_Commerce.DTOs.SellerDTO;
import com.keyur.E_Commerce.DTOs.SessionDTO;
import com.keyur.E_Commerce.Entities.UserSession;
import com.keyur.E_Commerce.ExceptionObjects.CustomerNotFoundException;
import com.keyur.E_Commerce.ExceptionObjects.SellerNotFoundException;
import com.keyur.E_Commerce.Security.CustomUserDetailsService;
import com.keyur.E_Commerce.Security.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class LoginLogoutServiceImp implements LoginLogoutSevice {
    //fields
    CustomUserDetailsService customUserDetailsService;
    JWTService jwtService;
    AuthenticationManager authenticationManager;

    //dependency injection
    LoginLogoutServiceImp(CustomUserDetailsService customUserDetailsService, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String loginCustomer(@RequestBody CustomerDTO customer) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            customer.getMobileId(),
                            customer.getPassword()
                    )
            );

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(customer.getMobileId());
            String token = jwtService.generateToken(userDetails);

            return token;

        } catch (AuthenticationException e) {
            throw new CustomerNotFoundException("No Customer found with the given mobile number!");
        }
    }


    @Override
    public String logoutCustomer() {
        return "Logged out successfully!";
    }

    @Override
    public String loginSeller(SellerDTO seller) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            seller.getMobile(),
                            seller.getPassword()
                    )
            );

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(seller.getMobile());
            String token = jwtService.generateToken(userDetails);
            return token;

        } catch (AuthenticationException e) {
            throw new SellerNotFoundException("No seller found with the given mobile number!");
        }
    }

    @Override
    public String logoutSeller(SessionDTO session) {
        return "Logged out successfully!";
    }

    public boolean checkSessionValidity(UserSession currentSession) {
        return false;
    }
}
