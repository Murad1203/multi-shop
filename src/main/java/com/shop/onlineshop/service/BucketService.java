package com.shop.onlineshop.service;

import com.shop.onlineshop.dto.BucketDTO;
import com.shop.onlineshop.model.Bucket;
import com.shop.onlineshop.model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BucketService {
    public Bucket createBucket(UserEntity user, List<Long> productsIds);
    public void addStudents(Bucket bucket, List<Long> productsId);
    public BucketDTO getBucketByUser(String name);
    public void deleteItem(String name, long id);
}
