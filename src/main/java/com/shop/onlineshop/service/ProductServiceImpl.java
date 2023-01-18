package com.shop.onlineshop.service;

import com.shop.onlineshop.model.Bucket;
import com.shop.onlineshop.model.Product;
import com.shop.onlineshop.model.UserEntity;
import com.shop.onlineshop.repository.ProductRepository;
import com.shop.onlineshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final UserRepository userRepository;

    private final BucketService bucketService;


    public ProductServiceImpl(ProductRepository repository, UserRepository userRepository, BucketService bucketService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.bucketService = bucketService;
    }


    @Override
    public List<Product> products() {
        return repository.findAll();
    }

    @Override
    public Product productById(Long id) {
        Product product = null;
        Optional<Product> product1 = repository.findById(id);

        if (product1.isPresent()) {
            product = product1.get();
        }
        return product;
    }

    @Override
    public void saveProduct(Product product) {
        repository.save(product);
    }

    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) {
        List<Product> products = products().stream()
                .filter(prod -> prod.getCategories().getId().equals(categoryId))
                .collect(Collectors.toList());
        return products;
    }


    @Override
    public void deleteProductById(Long id) {
        repository.deleteById(id);
    }


    @Override
    public List<Product> searchProducts(String nameProduct) {
        return products().stream().filter(prod -> prod.getProductName().toLowerCase().equals(nameProduct.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void addToUSerBucket(long productId, String email) {
        final Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        UserEntity user = optionalUser.get();

        Bucket bucket = user.getBucket();

        if (bucket == null) {
            Bucket newbucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newbucket);
            userRepository.save(user);
        } else {
            bucketService.addStudents(bucket, Collections.singletonList(productId));
        }
    }

    @Override
    public List<Product> filterAscending(List<Product> products) {
        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getPrice() - o2.getPrice();
            }
        });
        return products;

    }

    @Override
    public List<Product> filterDescending(List<Product> products) {
        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o2.getPrice() - o1.getPrice();
            }
        });
        return products;

    }

    @Override
    public List<Product> filterByPublicationsLast(List<Product> products) {
        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return Math.toIntExact(o2.getId() - o1.getId());
            }
        });
        return products;

    }

    @Override
    public List<Product> filterByPublicationsFirst(List<Product> products) {
        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return Math.toIntExact(o1.getId() - o2.getId());
            }
        });
        return products;

    }




}
