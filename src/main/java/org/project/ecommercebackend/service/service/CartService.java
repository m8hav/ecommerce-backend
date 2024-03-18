package org.project.ecommercebackend.service.service;

import org.project.ecommercebackend.dto.model.CartDTO;
import org.project.ecommercebackend.model.Cart;

import java.util.Optional;

public interface CartService {
    Optional<CartDTO> createCart(Long userId);

    Optional<CartDTO> createCart();

    Optional<CartDTO> getCart(Long userId);
    Optional<CartDTO> getCart();

    Cart getCartEntity(Long userId);

    Cart getCartEntity();

    Optional<CartDTO> addProductToCart(Long userId, Long productId);

    Optional<CartDTO> addProductToCart(Long productId);

    Optional<CartDTO> updateProductQuantityInCart(Long userId, Long productId, int quantity);

    Optional<CartDTO> updateProductQuantityInCart(Long productId, int quantity);

    Optional<CartDTO> removeProductFromCart(Long userId, Long productId);

    Optional<CartDTO> removeProductFromCart(Long productId);

    boolean clearCart(Long userId);

    boolean clearCart();

    void refreshCart(Long userId);

    void refreshCart();
}