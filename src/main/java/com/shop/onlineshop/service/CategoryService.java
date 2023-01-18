package com.shop.onlineshop.service;


import com.shop.onlineshop.model.Category;
import com.shop.onlineshop.model.Product;
import com.shop.onlineshop.repository.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategtoeyServiceInterface {


    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepo.findAll();
    }
    @Override
    public Category getCategory(Long categoryId) {
        Category category = null;
        Optional<Category> category1 = categoryRepo.findById(categoryId);
        if (category1.isPresent()) {
            category = category1.get();
        }
        return category;
    }
}
