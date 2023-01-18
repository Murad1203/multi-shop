package com.shop.onlineshop.controller;

import com.shop.onlineshop.dto.BucketDTO;
import com.shop.onlineshop.dto.Contact;
import com.shop.onlineshop.model.Category;
import com.shop.onlineshop.model.Product;
import com.shop.onlineshop.model.UserEntity;
import com.shop.onlineshop.service.BucketService;
import com.shop.onlineshop.service.CategoryService;
import com.shop.onlineshop.service.ContactService;
import com.shop.onlineshop.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/")
@Log4j2
public class ProductController {


    private final ProductService service;
    private final CategoryService categoryService;

    private final ContactService contactService;

    private final BucketService bucketService;

    @Value("${upload.path}")
    private String uploadPath;

    public ProductController(ProductService service, CategoryService categoryService, ContactService contactService, BucketService bucketService) {
        this.service = service;
        this.categoryService = categoryService;
        this.contactService = contactService;
        this.bucketService = bucketService;
    }

    @GetMapping("/admin/v1")
    public String adminPanel() {
        return "admin";
    }


    @GetMapping
    public String getAllProducts(Model model, Principal principal) {
        List<Product> products = service.products();
        List<Category> categories = categoryService.getAllCategory();

        if (principal != null) {
            model.addAttribute("amount", amount(principal.getName()).getAmountProducts());
        }
        else {
            model.addAttribute("amount", 0);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);

        return "index";
    }

    public BucketDTO amount(String email) {
        return bucketService.getBucketByUser(email);
    }

    @GetMapping("/catalog")
    public String catalogProducts(Model model, Principal principal) {
        List<Product> products = service.products();
        List<Category> categories = categoryService.getAllCategory();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        if (principal != null) {
            model.addAttribute("amount", amount(principal.getName()).getAmountProducts());
        }
        else {
            model.addAttribute("amount", 0);
        }
        return "shop";
    }

    //Фильтрация
    @GetMapping("/filter-price/ascending")
    public String filterAscending(Model model, Principal principal) {
        List<Product> productList = service.filterAscending(service.products());
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);

        model.addAttribute("products", productList);
        if (principal != null) {
            model.addAttribute("amount", amount(principal.getName()).getAmountProducts());
        }
        else {
            model.addAttribute("amount", 0);
        }
        return "ascending";
    }

    @GetMapping("/filter-price/descending")
    public String filterDescending(Model model, Principal principal) {
        List<Product> productList = service.filterDescending(service.products());
        model.addAttribute("products", productList);

        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);

        if (principal != null) {
            model.addAttribute("amount", amount(principal.getName()).getAmountProducts());
        }
        else {
            model.addAttribute("amount", 0);
        }
        return "descending";
    }

    @GetMapping("/filter/publications-last")
    public String filterPublicationsLast(Model model, Principal principal) {
        List<Product> productList = service.filterByPublicationsLast(service.products());
        model.addAttribute("products", productList);

        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);

        if (principal != null) {
            model.addAttribute("amount", amount(principal.getName()).getAmountProducts());
        }
        else {
            model.addAttribute("amount", 0);
        }
        return "last-pub";
    }

    @GetMapping("/filter/publications-first")
    public String filterPublicationsFirst(Model model, Principal principal) {

        List<Product> productList = service.filterByPublicationsFirst(service.products());
        model.addAttribute("products", productList);

        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);

        if (principal != null) {
            model.addAttribute("amount", amount(principal.getName()).getAmountProducts());
        }
        else {
            model.addAttribute("amount", 0);
        }
        return "first-pub";
    }



    @GetMapping("/details/{id}")
    public String getProductById(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("products", service.products());
        model.addAttribute("product", service.productById(id));

        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);

        if (principal != null) {
            model.addAttribute("amount", amount(principal.getName()).getAmountProducts());
        }
        else {
            model.addAttribute("amount", 0);
        }

        return "detail";
    }

    @GetMapping("/admin/v1/add-product")
    public String addStudentGet(Model model) {
        Product product = new Product();
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "admin-add-product";
    }

    @PostMapping("/admin/save-student")
    public String saveProduct(Product product,
                              @RequestParam("file") MultipartFile file) throws IOException {

        if (file != null) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            product.setFilename(resultFileName);
        }

        service.saveProduct(product);
        return "redirect:/";
    }

    @RequestMapping("/admin/v1/product/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        Product product = service.productById(id);
        model.addAttribute("product", product);
        return "update-product";
    }


    @PostMapping("/admin/update/{id}")
    public String updateProduct(@PathVariable Long id,Product product, Model model) {
        service.saveProduct(product);
        return "redirect:/";
    }

    @RequestMapping("/admin/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        service.deleteProductById(id);
        return "/redirect:/";
    }

    @GetMapping("/category/{id}")
    public String getProductsByCategory(@PathVariable long id, Principal principal, Model model) {
        List<Product> products =  service.getProductsByCategoryId(id);
        model.addAttribute("products", products);

        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);

        if (principal != null) {
            model.addAttribute("amount", amount(principal.getName()).getAmountProducts());
        }
        else {
            model.addAttribute("amount", 0);
        }
        return "products-categ";
    }

    @GetMapping("/search/{search}")
    public String search(@PathVariable String search, Principal principal, Model model) {
        List<Product> products = service.searchProducts(search);
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);

        if (principal != null) {
            model.addAttribute("amount", amount(principal.getName()).getAmountProducts());
        }
        else {
            model.addAttribute("amount", 0);
        }

        return "search";
    }

    @GetMapping("/contact")
    public String getContact(Principal principal, Model model) {

        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);

        if (principal != null) {
            model.addAttribute("amount", amount(principal.getName()).getAmountProducts());
        }
        else {
            model.addAttribute("amount", 0);
        }
        return "contact";
    }

    @PostMapping("/contact-use")
    public String contactUse(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("message") String message
    ) throws IOException, InterruptedException {
        Contact contact = new Contact(name, email, message);
        contactService.sendMessage(contact);
        return "redirect:/";
    }


    @GetMapping("/{id}/bucket")
    public String addBucker(@PathVariable long id, Principal principal) {
        if (principal == null) {
            return "redirect:/";
        }
        service.addToUSerBucket(id, principal.getName());
        return "redirect:/";
    }

}
