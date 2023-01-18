package com.shop.onlineshop.dto;


import com.shop.onlineshop.model.Product;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BucketDetailsDTO {
    private Long productId;
    private String productName;
    private String filename;
    private String description;
    private int price;

    public BucketDetailsDTO(Product product) {
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.filename = product.getFilename();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }
}
