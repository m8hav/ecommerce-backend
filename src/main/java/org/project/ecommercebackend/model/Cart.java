package org.project.ecommercebackend.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private Double total;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<CartProduct> cartProducts;

    public Cart() {
    }

    public Cart(Long id, Long userId, Double total, Set<CartProduct> cartProducts) {
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

    public Set<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(Set<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }
}
