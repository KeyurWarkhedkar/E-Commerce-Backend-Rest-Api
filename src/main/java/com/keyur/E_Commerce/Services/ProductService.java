package com.keyur.E_Commerce.Services;

import com.keyur.E_Commerce.DTOs.ProductDTO;
import com.keyur.E_Commerce.DTOs.SearchFilterDTO;
import com.keyur.E_Commerce.Entities.Product;
import com.keyur.E_Commerce.Enums.CategoryEnum;
import com.keyur.E_Commerce.Enums.ProductStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product addProductToCatalog(Product product);

    public Product getProductFromCatalogById(Integer id);

    public List<Product>  searchProductsByName(String name);

    public String deleteProductFromCatalog(Integer id);

    public Product updateProductIncatalog(Product product);

    public List<Product> getAllProductsIncatalog();

    public List<ProductDTO> getAllProductsOfSeller(Integer id);

    public List<ProductDTO> getProductsOfCategory(CategoryEnum catenum);

    public List<ProductDTO> getProductsOfStatus(ProductStatus status);

    public Product updateProductQuantityWithId(Integer id, ProductDTO prodDTO);

    public Page<Product> searchProduct(SearchFilterDTO req);

}
