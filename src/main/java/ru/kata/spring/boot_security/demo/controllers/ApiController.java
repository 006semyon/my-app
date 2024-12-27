package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> apiGetUsers() {
        return userService.getAll();
    }

    @GetMapping("/users/{id}")
    public User apiGetUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public User apiPost(@RequestBody User user) {
        List<Integer> roleIds = user.getRoles().stream()
                .map(role -> Integer.parseInt(role.getRole()))
                .collect(Collectors.toList());
        userService.add(user, roleIds);
        return user;
    }

    @PatchMapping("/users")
    public void apiPatch(@RequestBody User user) {
        List<Integer> roleIds = null;
        if (user.getRoles() != null) {
            roleIds = user.getRoles().stream()
                    .map(role -> Integer.parseInt(role.getRole()))
                    .collect(Collectors.toList());
        }
        userService.update(user.getId(), user, roleIds);
    }

    @DeleteMapping("/users/{id}")
    public void apiDelete(@PathVariable Integer id) {
        userService.delete(id);
    }

    @GetMapping("/roles")
    public List<Role> getRoles() {
        return userService.getRoles();
    }
}
