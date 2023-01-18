package com.shop.onlineshop.controller;


import com.shop.onlineshop.model.UserEntity;
import com.shop.onlineshop.service.UserService;
import com.shop.onlineshop.service.token.ConfirmationToken;
import com.shop.onlineshop.service.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping("/sign-in")
    public String signIn() {
        return "login";
    }

    @GetMapping("/sign-up")
    String signUp(Model model) {
        UserEntity userEntity = new UserEntity();
        model.addAttribute("user", userEntity);
        return "register";
    }

    @PostMapping("/sign-up")
    String signUp(UserEntity user) {
        userService.signUpUser(user);

        return "redirect:/sign-in";
    }

    @GetMapping("/sign-up/confirm")
    public String confirmMail(@RequestParam("token") String token) {
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

        optionalConfirmationToken.ifPresent(userService::confirmUser);
        log.info(" ==== Успешное подтверждение почты");
        return "successful";
    }





}
