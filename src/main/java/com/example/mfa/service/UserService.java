package com.example.mfa.service;

import com.example.mfa.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final Map<String, User> users = Map.of(
            "rakesh", new User("rakesh", "Password@123"),
            "admin", new User("admin", "Admin@123")
    );

    public boolean isValidUser(String username, String password) {
        return Optional.ofNullable(users.get(username))
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    public boolean exists(String username) {
        return users.containsKey(username);
    }
}
