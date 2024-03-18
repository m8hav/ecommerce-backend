package org.project.ecommercebackend.service.service;

import org.project.ecommercebackend.model.CartProduct;

import java.util.List;
import java.util.Optional;

public interface CartProductService {
    Optional<CartProduct> createCartProduct(Long productId, Long cartId);
    Optional<CartProduct> getCartProduct(Long productId, Long cartId);
    List<CartProduct> getCartProducts(Long cartId);
    Optional<CartProduct> updateCartProduct(CartProduct cartProduct, int quantity);
    Optional<CartProduct> updateCartProduct(Long productId, int quantity, Long cartId);
    boolean removeCartProduct(CartProduct cartProduct);
    boolean removeCartProduct(Long productId, Long cartId);
}
