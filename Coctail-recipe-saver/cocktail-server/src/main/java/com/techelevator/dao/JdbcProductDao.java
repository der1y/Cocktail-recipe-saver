package com.techelevator.dao;

import com.techelevator.model.Product;

import java.util.List;

public class JdbcProductDao implements ProductDao {
    @Override
    public List<Product> getProducts() {
        return List.of();
    }

    @Override
    public List<Product> getProductsByNameOrSku(String productName, String productSku) {
        return List.of();
    }

    @Override
    public Product getProductDetails(int productId) {
        return null;
    }

    @Override
    public List<Product> getProductsInCart() {
        return List.of();
    }
}
