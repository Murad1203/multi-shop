package com.shop.onlineshop.controller;

import com.shop.onlineshop.dto.BucketDTO;
import com.shop.onlineshop.service.BucketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class BucketController {

    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }


    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("bucket",  new BucketDTO());
        }
        else {
            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDTO);
        }
        return "cart";
    }

    @GetMapping("/bucket/{id}")
    public String deleteItem(@PathVariable long id, Principal principal) {
        bucketService.deleteItem(principal.getName(), id);
        return "redirect:/bucket";

    }

}
