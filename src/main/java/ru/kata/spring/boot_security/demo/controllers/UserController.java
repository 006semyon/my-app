package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.TaxPayment;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.*;


@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/taxPayments")
    @ResponseBody
    public Map<String, Object> user(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new RuntimeException("User not found for principal: " + principal.getName());
        }

        Set<TaxPayment> taxPayments = user.getTaxPayments();
        if (taxPayments == null) {
            throw new RuntimeException("taxPayments is null"); // Предотвращаем NullPointerException
        }
        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("taxPayments", user.getTaxPayments());
        return response;
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model) {
        User user =  userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/index";
    }
}
