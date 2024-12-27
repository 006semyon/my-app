package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    void add(User user, List<Integer> roles);
    void update(int id, User user, List<Integer> roles);
    void delete(int id);
    List<User> getAll();
    User getUserById(int id);
    List<Role> getRoles();
}
