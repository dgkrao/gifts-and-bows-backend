package com.giftsandbows.controller;

import com.giftsandbows.model.CartItem;
import com.giftsandbows.model.User;
import com.giftsandbows.repository.CartRepository;
import com.giftsandbows.repository.UserRepository;
import com.giftsandbows.util.SecurityUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class CartController {

    private final CartRepository cartRepo;
    private final UserRepository userRepo;

    public CartController(CartRepository cartRepo, UserRepository userRepo) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/me")
    public List<CartItem> getMyCart() {

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepo.findByUserId(user.getId());
    }

    @PostMapping
    public CartItem add(@RequestBody CartItem item) {

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        item.setUserId(user.getId());
        return cartRepo.save(item);
    }

    @DeleteMapping("/clear")
    public void clearMyCart() {

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        cartRepo.deleteByUserId(user.getId());
    }
}
