package com.keyur.E_Commerce.Services;

import com.keyur.E_Commerce.DTOs.SellerDTO;
import com.keyur.E_Commerce.DTOs.SessionDTO;
import com.keyur.E_Commerce.Entities.Seller;
import com.keyur.E_Commerce.ExceptionObjects.SellerException;

import java.util.List;

public interface SellerService {
    public Seller addSeller(Seller seller);

    public Seller getSellerById(Integer sellerId)throws SellerException;

    public Seller getSellerByMobile(String mobile) throws SellerException;

    public Seller getCurrentlyLoggedInSeller() throws SellerException;

    public String updateSellerPassword(SellerDTO sellerDTO) throws SellerException;

    public Seller updateSeller(Seller seller)throws SellerException;

    //public Seller updateSellerMobile(SellerDTO sellerdto, String token)throws SellerException;

    public Seller deleteSellerById(Integer sellerId)throws SellerException;
}
