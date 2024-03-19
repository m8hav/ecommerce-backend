package org.project.ecommercebackend.dto.model;

import java.util.Set;

public class CartDTO {
    private Long id;
    private Long userId;
    private Double total;
    private Set<CartProductDTO> cartProducts;

    public CartDTO() {
    }

    public CartDTO(Long id, Long userId, Double total, Set<CartProductDTO> cartProducts) {
        this.id = id;
        this.userId = userId;
        this.total = total;
        this.cartProducts = cartProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Set<CartProductDTO> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(Set<CartProductDTO> cartProducts) {
        this.cartProducts = cartProducts;
    }
}
