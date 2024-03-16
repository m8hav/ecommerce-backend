package org.project.ecommercebackend.repository;

import org.project.ecommercebackend.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    CartProduct findByProductIdAndCartId(Long productId, Long cartId);
    List<CartProduct> findByCartId(Long cartId);
}
