package com.giftsandbows.controller;

import com.giftsandbows.config.JwtUtil;
import com.giftsandbows.model.User;
import com.giftsandbows.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepo.save(user);
        return "Registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody User req) {
        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return JwtUtil.generateToken(user.getEmail(), user.getRole());
    }
}
