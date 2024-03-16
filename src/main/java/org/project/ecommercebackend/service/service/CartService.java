package org.project.ecommercebackend.service.service;

import org.project.ecommercebackend.dto.model.CartDTO;
import org.project.ecommercebackend.dto.model.CartProductDTO;
import org.project.ecommercebackend.dto.model.OrderDTO;
import org.project.ecommercebackend.model.Cart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CartService {
    Optional<CartDTO> createCart(Long userId);
    Optional<CartDTO> getCart(Long userId);

    Cart getCartEntity(Long userId);

    Optional<CartDTO> addProductToCart(Long userId, Long productId);
    Optional<CartDTO> updateProductQuantityInCart(Long userId, Long productId, int quantity);

    Optional<CartDTO> removeProductFromCart(Long userId, Long productId);
    boolean clearCart(Long userId);

    void refreshCart(Long userId);
}