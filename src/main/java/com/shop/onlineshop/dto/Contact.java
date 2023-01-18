package com.shop.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Contact {
    private String name;
    private String email;
    private String message;
}
