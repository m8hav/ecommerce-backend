package org.project.ecommercebackend.controller;

import org.project.ecommercebackend.dto.model.CartDTO;
import org.project.ecommercebackend.dto.request.CartRequestDTO;
import org.project.ecommercebackend.service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/v1/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

//    @GetMapping("/user/{userId}")
//    public CartDTO getCart(@PathVariable Long userId) {
//        if (userId == null) {
//            throw new IllegalArgumentException("User id is required");
//        }
//        return cartService.getCart(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
//    }

    @GetMapping
    public CartDTO getCart() {
        return cartService.getCart().orElseThrow(() -> new RuntimeException("Cart not found"));
    }

//    @PostMapping("/product/add")
//    public CartDTO addProductToCart(@RequestBody CartRequestDTO cartRequestDTO) {
//        if (cartRequestDTO == null || cartRequestDTO.getUserId() == null || cartRequestDTO.getProductId() == null) {
//            throw new IllegalArgumentException("User id and product id are required");
//        }
//        return cartService.addProductToCart(cartRequestDTO.getUserId(), cartRequestDTO.getProductId())
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//    }
    @PostMapping("/{productId}")
    public CartDTO addProductToCart(@PathVariable Long productId) {
        return cartService.addProductToCart(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

//    @PutMapping("/product")
//    public CartDTO updateProductQuantityInCart(@RequestBody CartRequestDTO cartRequestDTO) {
//        if (cartRequestDTO == null || cartRequestDTO.getUserId() == null || cartRequestDTO.getProductId() == null || cartRequestDTO.getQuantity() == 0) {
//            throw new IllegalArgumentException("User id, product id and quantity are required");
//        }
//        return cartService.updateProductQuantityInCart(cartRequestDTO.getUserId(), cartRequestDTO.getProductId(), cartRequestDTO.getQuantity())
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//    }


    @PutMapping
    public CartDTO updateProductQuantityInCart(@RequestBody CartRequestDTO cartRequestDTO) {
        if (cartRequestDTO == null || cartRequestDTO.getProductId() == null) {
            throw new IllegalArgumentException("Product id and quantity are required");
        }
        return cartService.updateProductQuantityInCart(cartRequestDTO.getProductId(), cartRequestDTO.getQuantity())
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

//    @PutMapping("/product/remove")
//    public CartDTO removeProductFromCart(@RequestBody CartRequestDTO cartRequestDTO) {
//        if (cartRequestDTO == null || cartRequestDTO.getUserId() == null || cartRequestDTO.getProductId() == null) {
//            throw new IllegalArgumentException("User id and product id are required");
//        }
//        return cartService.removeProductFromCart(cartRequestDTO.getUserId(), cartRequestDTO.getProductId())
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//    }


    @DeleteMapping("/{productId}")
    public CartDTO removeProductFromCart(@PathVariable Long productId) {
        return cartService.removeProductFromCart(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

//    @DeleteMapping("/user/{userId}")
//    public boolean clearCart(@PathVariable Long userId) {
//        return cartService.clearCart(userId);
//    }

    @DeleteMapping
    public boolean clearCart() {
        return cartService.clearCart();
    }
}
