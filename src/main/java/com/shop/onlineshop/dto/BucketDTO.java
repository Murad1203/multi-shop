package com.shop.onlineshop.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BucketDTO {
    private int amountProducts;

    private List<BucketDetailsDTO> bucketDetails = new ArrayList<>();

    public void aggregate() {
        this.amountProducts = bucketDetails.size();
    }


}
