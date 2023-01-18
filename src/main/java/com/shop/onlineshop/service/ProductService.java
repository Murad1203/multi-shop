package com.shop.onlineshop.service;

import com.shop.onlineshop.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    List<Product> products();
    Product productById(Long id);
    void saveProduct(Product product);

    public List<Product> getProductsByCategoryId(Long categoryId);
//    void update(Long id);
    void deleteProductById(Long id);

    List<Product> searchProducts(String nameProduct);

    public void addToUSerBucket(long productId, String email);
    List<Product> filterAscending(List<Product> products);

    List<Product> filterDescending(List<Product> products);

    List<Product> filterByPublicationsLast(List<Product> products);
    List<Product> filterByPublicationsFirst(List<Product> products);
}
