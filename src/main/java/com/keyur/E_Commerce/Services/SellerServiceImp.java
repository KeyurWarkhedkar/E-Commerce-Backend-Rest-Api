package com.keyur.E_Commerce.Services;

import com.keyur.E_Commerce.DTOs.SellerDTO;
import com.keyur.E_Commerce.DTOs.SessionDTO;
import com.keyur.E_Commerce.Entities.Seller;
import com.keyur.E_Commerce.Entities.UserSession;
import com.keyur.E_Commerce.ExceptionObjects.LoginException;
import com.keyur.E_Commerce.ExceptionObjects.SellerException;
import com.keyur.E_Commerce.ExceptionObjects.SellerNotFoundException;
import com.keyur.E_Commerce.Repositories.SellerDao;
import com.keyur.E_Commerce.Repositories.SessionDao;
import com.keyur.E_Commerce.Security.SecurityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImp implements SellerService{
    //fields
    SellerDao sellerDao;
    SessionDao sessionDao;
    LoginLogoutServiceImp loginLogoutServiceImp;
    SecurityUtils securityUtils;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //injecting dependencies using constructor injection
    SellerServiceImp(SellerDao sellerDao, SessionDao sessionDao, LoginLogoutServiceImp loginLogoutServiceImp, SecurityUtils securityUtils, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.sellerDao = sellerDao;
        this.sessionDao = sessionDao;
        this.loginLogoutServiceImp = loginLogoutServiceImp;
        this.securityUtils = securityUtils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Seller addSeller(Seller seller) throws SellerException {
        //check if the seller with the given mobile number already exists or not
        Optional<Seller> check = sellerDao.findByMobile(seller.getMobile());

        //throw an error if the user already exists
        if(check.isPresent()) {
            throw new SellerException("Seller already registered!");
        }

        //set the bcrypt password for the seller
        seller.setPassword(bCryptPasswordEncoder.encode(seller.getPassword()));

        //set the required fields for the seller
        seller.setProduct(new ArrayList<>());

        //set the role of the seller for future authentication
        seller.setRole("ROLE_SELLER");

        //save the seller to the database
        sellerDao.save(seller);

        return seller;
    }

    @Override
    public Seller getSellerById(Integer sellerId) throws SellerNotFoundException {
        //check if any seller with the given id exists in the database
        Optional<Seller> check = sellerDao.findById(sellerId);

        //if no seller exists with the given id, throw the appropriate error
        if(check.isEmpty()) {
            throw new SellerNotFoundException("No seller with the given id found");
        }

        return check.get();
    }

    @Override
    public Seller getSellerByMobile(String mobile) throws SellerNotFoundException {
        //made for admin access

        //check if any seller with the given mobile number exists or not
        Optional<Seller> checkSeller = sellerDao.findByMobile(mobile);

        //if no seller exists, throw appropriate error
        if(checkSeller.isEmpty()) {
            throw new SellerNotFoundException("No seller with given mobile number exists!");
        }

        return checkSeller.get();
    }

    @Override
    public Seller getCurrentlyLoggedInSeller() throws SellerNotFoundException {
        return securityUtils.getCurrentSeller();
    }

    @Override
    public String updateSellerPassword(SellerDTO sellerDTO) throws SellerException, LoginException, SellerNotFoundException {
        //get the currently logged in seller from spring security context
        Seller seller = securityUtils.getCurrentSeller();

        //authenticate the seller with mobile and number and update his password
        if(seller.getMobile().equals(sellerDTO.getMobile())) {
            if(sellerDTO.getPassword() != null) {
                seller.setPassword(bCryptPasswordEncoder.encode(sellerDTO.getPassword()));
                sellerDao.save(seller);
                loginLogoutServiceImp.logoutCustomer();
            } else {
                throw new SellerException("Cannot update with blank password!");
            }
        } else {
            throw new SellerException("Mobile number authentication failed. Please try again!");
        }
        return "Password updated and logged out successfully. Login again with new password!";
    }

    @Override
    public Seller updateSeller(Seller seller) throws SellerException, LoginException, SellerNotFoundException {
        //fetch the current seller from spring security context
        Seller fromDatabase = securityUtils.getCurrentSeller();

        if(seller.getFirstName() != null) {
            fromDatabase.setFirstName(seller.getFirstName());
        }
        if(seller.getLastName() != null) {
            fromDatabase.setLastName(seller.getLastName());
        }
        if(seller.getPassword() != null) {
            fromDatabase.setPassword(bCryptPasswordEncoder.encode(seller.getPassword()));
        }
        if(seller.getMobile() != null) {
            fromDatabase.setMobile(seller.getMobile());
        }
        if(seller.getEmailId() != null) {
            fromDatabase.setEmailId(seller.getEmailId());
        }

        sellerDao.save(fromDatabase);

        return fromDatabase;
    }

    @Override
    public Seller deleteSellerById(Integer sellerId) throws SellerException, LoginException, SellerNotFoundException {
        //fetch the current seller from spring security context
        Seller seller = securityUtils.getCurrentSeller();

        //check if the id of the seller from the session matches with the id provided in the argument
        if(!seller.getSellerId().equals(sellerId)) {
            throw new SellerException("Id does not match with the session id!");
        }

        //delete the seller from the database
        sellerDao.delete(seller);

        //log out the customer
        loginLogoutServiceImp.logoutCustomer();

        return seller;
    }
}
