package com.keyur.E_Commerce.Controllers;

import com.keyur.E_Commerce.DTOs.ProductDTO;
import com.keyur.E_Commerce.Entities.Product;
import com.keyur.E_Commerce.Enums.CategoryEnum;
import com.keyur.E_Commerce.Enums.ProductStatus;
import com.keyur.E_Commerce.Services.ProductServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    //fields
    ProductServiceImp pService;

    //dependency injection
    ProductController(ProductServiceImp pService) {
        this.pService = pService;
    }

    @PostMapping("/seller/products")
    public ResponseEntity<Product> addProductToCatalogHandler(@Valid @RequestBody Product product) {

        Product prod = pService.addProductToCatalog(product);

        return new ResponseEntity<Product>(prod, HttpStatus.ACCEPTED);

    }

    // This method gets the product which needs to be added to the cart returns
    // product

    @GetMapping("/seller/product/{id}")
    public ResponseEntity<Product> getProductFromCatalogByIdHandler(@PathVariable("id") Integer id) {

        Product prod = pService.getProductFromCatalogById(id);

        return new ResponseEntity<Product>(prod, HttpStatus.FOUND);

    }

    // This method will delete the product from catalog and returns the response
    // This will be called only when the product qty will be zero or seller wants to
    // delete for any other reason

    @DeleteMapping("/seller/product/{id}")
    public ResponseEntity<String> deleteProductFromCatalogHandler(@PathVariable("id") Integer id) {

        String res = pService.deleteProductFromCatalog(id);
        return new ResponseEntity<String>(res, HttpStatus.OK);
    }

    @PutMapping("/seller/products")
    public ResponseEntity<Product> updateProductInCatalogHandler(@Valid @RequestBody Product prod) {

        Product prod1 = pService.updateProductIncatalog(prod);

        return new ResponseEntity<Product>(prod1, HttpStatus.OK);

    }

    @GetMapping("/seller/products")
    public ResponseEntity<List<Product>> getAllProductsHandler() {

        List<Product> list = pService.getAllProductsIncatalog();

        return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
    }

    //this method gets the products mapped to a particular seller
    @GetMapping("/customer/products/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductsOfSellerHandler(@PathVariable("id") Integer id) {

        List<ProductDTO> list = pService.getAllProductsOfSeller(id);

        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);
    }

    @GetMapping("/seller/products/{catenum}")
    public ResponseEntity<List<ProductDTO>> getAllProductsInCategory(@PathVariable("catenum") String catenum) {
        CategoryEnum ce = CategoryEnum.valueOf(catenum.toUpperCase());
        List<ProductDTO> list = pService.getProductsOfCategory(ce);
        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);

    }

    @GetMapping("/seller/products/status/{status}")
    public ResponseEntity<List<ProductDTO>> getProductsWithStatusHandler(@PathVariable("status") String status) {

        ProductStatus ps = ProductStatus.valueOf(status.toUpperCase());
        List<ProductDTO> list = pService.getProductsOfStatus(ps);

        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);

    }


    @PutMapping("/seller/products/{id}")
    public ResponseEntity<Product> updateQuantityOfProduct(@PathVariable("id") Integer id, @RequestBody ProductDTO prodDto){

        Product prod =   pService.updateProductQuantityWithId(id, prodDto);

        return new ResponseEntity<Product>(prod,HttpStatus.ACCEPTED);
    }
}
