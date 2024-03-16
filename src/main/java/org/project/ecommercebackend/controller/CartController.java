package org.project.ecommercebackend.controller;

import org.project.ecommercebackend.dto.model.CartDTO;
import org.project.ecommercebackend.dto.request.CartRequestDTO;
import org.project.ecommercebackend.service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart/user/{userId}")
    public CartDTO getCart(@PathVariable Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id is required");
        }
        return cartService.getCart(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @PostMapping("/cart/product")
    public CartDTO addProductToCart(@RequestBody CartRequestDTO cartRequestDTO) {
        if (cartRequestDTO == null || cartRequestDTO.getUserId() == null || cartRequestDTO.getProductId() == null) {
            throw new IllegalArgumentException("User id and product id are required");
        }
        return cartService.addProductToCart(cartRequestDTO.getUserId(), cartRequestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @PutMapping("/cart/product")
    public CartDTO updateProductQuantityInCart(@RequestBody CartRequestDTO cartRequestDTO) {
        if (cartRequestDTO == null || cartRequestDTO.getUserId() == null || cartRequestDTO.getProductId() == null || cartRequestDTO.getQuantity() == 0) {
            throw new IllegalArgumentException("User id, product id and quantity are required");
        }
        return cartService.updateProductQuantityInCart(cartRequestDTO.getUserId(), cartRequestDTO.getProductId(), cartRequestDTO.getQuantity())
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @PutMapping("/cart/product/remove")
    public CartDTO removeProductFromCart(@RequestBody CartRequestDTO cartRequestDTO) {
        if (cartRequestDTO == null || cartRequestDTO.getUserId() == null || cartRequestDTO.getProductId() == null) {
            throw new IllegalArgumentException("User id and product id are required");
        }
        return cartService.removeProductFromCart(cartRequestDTO.getUserId(), cartRequestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @DeleteMapping("/cart/user/{userId}")
    public boolean clearCart(@PathVariable Long userId) {
        return cartService.clearCart(userId);
    }
}
