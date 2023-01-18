package com.shop.onlineshop.service;

import com.shop.onlineshop.model.Category;
import com.shop.onlineshop.model.Product;

import java.util.List;

public interface CategtoeyServiceInterface {
    public List<Category> getAllCategory();
    public Category getCategory(Long categoryId);
//    public List<Product> getProductsByCategoryId(Long id);
}
