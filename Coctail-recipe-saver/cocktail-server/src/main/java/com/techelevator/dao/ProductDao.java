package com.techelevator.dao;

import com.techelevator.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();

    List<Product> getProductsByNameOrSku(String productName, String productSku);

    Product getProductDetails(int productId);

    List<Product> getProductsInCart();
}
