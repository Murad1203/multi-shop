package com.shop.onlineshop.service;

import com.shop.onlineshop.dto.BucketDTO;
import com.shop.onlineshop.dto.BucketDetailsDTO;
import com.shop.onlineshop.model.Bucket;
import com.shop.onlineshop.model.Product;
import com.shop.onlineshop.model.UserEntity;
import com.shop.onlineshop.repository.BucketRepository;
import com.shop.onlineshop.repository.ProductRepository;
import com.shop.onlineshop.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Override
    public Bucket createBucket(UserEntity user, List<Long> productsIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productList = getCollectRefStudentsByIds(productsIds);
        bucket.setProducts(productList);
        return bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefStudentsByIds(List<Long> productsIds) {
        return productsIds.stream()
                .map((Long id) -> productRepository.getById((long) Math.toIntExact(id)))
                .collect(Collectors.toList());
    }

    @Override
    public void addStudents(Bucket bucket, List<Long> productsId) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefStudentsByIds(productsId));
        bucket.setProducts(newProductList);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDTO getBucketByUser(String name) {
        final Optional<UserEntity> optionalUser = userRepository.findByEmail(name);
        UserEntity user = optionalUser.get();

        if (user.getBucket() == null) {
            return new BucketDTO();
        }
        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailsDTO> mapByProductId = new HashMap<>();
        List<Product> productList = user.getBucket().getProducts();
        for (Product product : productList) {
            BucketDetailsDTO detail = mapByProductId.get(product.getId());
            if (detail == null) {
                mapByProductId.put(product.getId(), new BucketDetailsDTO(product));
            }
        }

        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate();
        return bucketDTO;
    }

    @Override
    public void deleteItem(String name, long id) {
        final Optional<UserEntity> optionalUser = userRepository.findByEmail(name);
        UserEntity user = optionalUser.get();

        Bucket bucket = user.getBucket();
        List<Product> products = user.getBucket().getProducts();
        products.removeIf(product -> product.getId().equals(id));
        bucket.setProducts(products);
        bucketRepository.save(bucket);
    }
}
