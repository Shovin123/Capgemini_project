// controller/AuthController.java
package com.example.security.controller;

import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register/save")
    public String saveUser(@ModelAttribute User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        
        // Set a default role if not set
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER");  // Must start with ROLE_ by convention
        }
        
        userRepository.save(user);
        return "redirect:/login";
    }


    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
