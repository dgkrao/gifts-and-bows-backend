package com.giftsandbows.controller;

import com.giftsandbows.model.OrderEntity;
import com.giftsandbows.model.Product;
import com.giftsandbows.repository.OrderRepository;
import com.giftsandbows.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    public OrderController(OrderRepository orderRepo,
                           ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    /* ================= PLACE ORDER ================= */

    @PostMapping
    public OrderEntity placeOrder(@RequestBody OrderEntity order) {

        // 🔒 Basic validation
        if (order.getUserId() == null) {
            throw new RuntimeException("User ID required");
        }

        // ⚠️ TEMP LOGIC (single-product order)
        // Later we’ll extend to cart-based orders

        Long productId = order.getProductId(); // must be sent from frontend
        int orderedQty = order.getQuantity();  // must be sent from frontend

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() < orderedQty) {
            throw new RuntimeException("Insufficient stock");
        }

        // 🔻 Reduce stock
        product.setQuantity(product.getQuantity() - orderedQty);
        productRepo.save(product);

        // 🧾 Finalize order
        order.setStatus("PROCESSING");
        order.setPaymentMethod("UPI");

        return orderRepo.save(order);
    }

    /* ================= USER ORDERS ================= */

    @GetMapping("/{userId}")
    public List<OrderEntity> myOrders(@PathVariable Long userId) {
        return orderRepo.findByUserId(userId);
    }

    /* ================= ADMIN UPDATE STATUS ================= */

    @PutMapping("/admin/{orderId}/{status}")
    public void updateStatus(
            @PathVariable Long orderId,
            @PathVariable String status
    ) {
        OrderEntity order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        orderRepo.save(order);
    }
}
