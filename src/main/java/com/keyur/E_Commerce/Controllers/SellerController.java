package com.keyur.E_Commerce.Controllers;

import com.keyur.E_Commerce.DTOs.SellerDTO;
import com.keyur.E_Commerce.Entities.Seller;
import com.keyur.E_Commerce.Services.SellerServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {
    //fields
    SellerServiceImp sService;

    //dependency injection
    SellerController(SellerServiceImp sService) {
        this.sService = sService;
    }


    //Get the list of seller-----------------------
    /*@GetMapping("/sellers")
    public ResponseEntity<List<Seller>> getAllSellerHandler(){

        List<Seller> sellers=sService.getAllSellers();

        return new ResponseEntity<List<Seller>>(sellers, HttpStatus.OK);
    }*/


    //Get the seller by Id............................
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Seller> getSellerByIdHandler(@PathVariable("sellerId") Integer Id){

        Seller getSeller=sService.getSellerById(Id);

        return new ResponseEntity<Seller>(getSeller, HttpStatus.OK);
    }


    // Get Seller by mobile Number
    @GetMapping("/seller")
    public ResponseEntity<Seller> getSellerByMobileHandler(@RequestParam("mobile") String mobile){

        Seller getSeller=sService.getSellerByMobile(mobile);

        return new ResponseEntity<Seller>(getSeller, HttpStatus.OK);
    }


    // Get currently logged in seller
    @GetMapping("/seller/current")
    public ResponseEntity<Seller> getLoggedInSellerHandler(){

        Seller getSeller = sService.getCurrentlyLoggedInSeller();

        return new ResponseEntity<Seller>(getSeller, HttpStatus.OK);
    }

    //Update the seller..............................
    @PutMapping("/seller")
    public ResponseEntity<Seller> updateSellerHandler(@RequestBody Seller seller){
        Seller updatedseller=sService.updateSeller(seller);

        return new ResponseEntity<Seller>(updatedseller, HttpStatus.ACCEPTED);

    }


    /*@PutMapping("/seller/update/mobile")
    public ResponseEntity<Seller> updateSellerMobileHandler(@Valid @RequestBody SellerDTO sellerdto, @RequestHeader("token") String token){
        Seller updatedseller=sService.updateSellerMobile(sellerdto, token);

        return new ResponseEntity<Seller>(updatedseller,HttpStatus.ACCEPTED);

    }*/


    @PutMapping("/seller/update/password")
    public ResponseEntity<String> updateSellerPasswordHandler(@Valid @RequestBody SellerDTO sellerDto){
        return new ResponseEntity<>(sService.updateSellerPassword(sellerDto), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/seller/{sellerId}")
    public ResponseEntity<Seller> deleteSellerByIdHandler(@PathVariable("sellerId") Integer Id){

        Seller deletedSeller=sService.deleteSellerById(Id);

        return new ResponseEntity<Seller>(deletedSeller,HttpStatus.OK);

    }
}
