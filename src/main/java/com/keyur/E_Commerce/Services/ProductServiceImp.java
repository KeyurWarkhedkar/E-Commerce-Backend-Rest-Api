package com.keyur.E_Commerce.Services;

import com.keyur.E_Commerce.DTOs.ProductDTO;
import com.keyur.E_Commerce.DTOs.SearchFilterDTO;
import com.keyur.E_Commerce.Entities.Product;
import com.keyur.E_Commerce.Entities.Seller;
import com.keyur.E_Commerce.Enums.CategoryEnum;
import com.keyur.E_Commerce.Enums.ProductStatus;
import com.keyur.E_Commerce.ExceptionObjects.ProductNotFoundException;
import com.keyur.E_Commerce.Repositories.ProductDao;
import com.keyur.E_Commerce.Repositories.SellerDao;
import com.keyur.E_Commerce.Security.SecurityUtils;
import com.keyur.E_Commerce.Specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService{
    //fields
    ProductDao productDao;
    SellerDao sellerDao;
    SellerServiceImp sellerServiceImp;
    SecurityUtils securityUtils;

    //injecting dependencies using constructor injection
    ProductServiceImp(ProductDao productDao, SellerDao sellerDao, SellerServiceImp sellerServiceImp, SecurityUtils securityUtils) {
        this.productDao = productDao;
        this.sellerDao = sellerDao;
        this.sellerServiceImp = sellerServiceImp;
        this.securityUtils = securityUtils;
    }

    @Override
    public Product addProductToCatalog(Product product) {
        Seller seller = securityUtils.getCurrentSeller();

        //check if the same product already exists in the database or not
        Optional<Product> optionalProduct = productDao.findBySeller_SellerIdAndProductName(seller.getSellerId(), product.getProductName());

        //set other fields for the product
        product.setSeller(seller);
        product.setStatus(ProductStatus.AVAILABLE);

        if(optionalProduct.isPresent()) {
            //if the product already exists in the catalog then the quantity will be + 1 from
            //the previous quantity
            Product existingProduct = optionalProduct.get();
            existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());
            return productDao.save(existingProduct);
        } else {
            //if the product does not exist in the catalog, then it's quantity will be just 1
            product.setQuantity(product.getQuantity());
            return productDao.save(product);
        }
    }

    @Override
    public Product getProductFromCatalogById(Integer id) {
        //check if there is any product with the given id
        Optional<Product> optionalProduct = productDao.findById(id);

        //if there is no such throw an appropriate error
        if(optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("No product found with the given id");
        }

        //convert the optional value to product value
        return optionalProduct.get();
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return List.of();
    }

    @Override
    public String deleteProductFromCatalog(Integer id) {
        //check if any product with the given id exists in the database or not
        Optional<Product> optionalProduct = productDao.findById(id);

        //throw appropriate error if no product exists
        if(optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("No product with the given id found");
        }

        //convert the optional value to product value
        Product product = optionalProduct.get();

        //remove the product for seller list to avoid errors from use if in-memory objects
        Seller seller = product.getSeller();
        seller.getProduct().remove(product);
        product.setSeller(null);

        //delete the product from database
        productDao.delete(product);

        return "Product deleted successfully!";
    }

    @Override
    public Product updateProductIncatalog(Product product) {
        return null;
    }

    @Override
    public List<Product> getAllProductsIncatalog() {
        return List.of();
    }

    @Override
    public List<ProductDTO> getAllProductsOfSeller(Integer id) {
        return List.of();
    }

    @Override
    public List<ProductDTO> getProductsOfCategory(CategoryEnum catenum) {
        return List.of();
    }

    @Override
    public List<ProductDTO> getProductsOfStatus(ProductStatus status) {
        return List.of();
    }

    @Override
    public Product updateProductQuantityWithId(Integer id, ProductDTO prodDTO) {
        return null;
    }

    @Override
    public Page<Product> searchProduct(SearchFilterDTO req) {
        Sort sort = req.getSortDir().equalsIgnoreCase("asc") ?
                Sort.by(req.getSortBy()).ascending() :
                Sort.by(req.getSortBy()).descending();

        Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), sort);

        Specification<Product> spec = ProductSpecification.getSpec(req);

        return productDao.findAll(spec, pageable);
    }
}
