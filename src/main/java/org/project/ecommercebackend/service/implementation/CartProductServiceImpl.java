package org.project.ecommercebackend.service.implementation;

import org.project.ecommercebackend.dto.model.ProductDTO;
import org.project.ecommercebackend.model.Cart;
import org.project.ecommercebackend.model.CartProduct;
import org.project.ecommercebackend.repository.CartProductRepository;
import org.project.ecommercebackend.repository.CartRepository;
import org.project.ecommercebackend.service.service.CartProductService;
import org.project.ecommercebackend.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartProductServiceImpl implements CartProductService {
    private final CartProductRepository cartProductRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;

    @Autowired
    public CartProductServiceImpl(CartProductRepository cartProductRepository, CartRepository cartRepository, ProductService productService) {
        this.cartProductRepository = cartProductRepository;
        this.cartRepository = cartRepository;
        this.productService = productService;
    }
    @Override
    public Optional<CartProduct> createCartProduct(Long productId, Long cartId) {
        if (productId == null || cartId == null)
            throw new IllegalArgumentException("Product and cart ids must be provided");
        ProductDTO productDTO = productService.getProduct(productId).orElse(null);
        if (productDTO == null)
            throw new IllegalArgumentException("Product with this id does not exist");
        Cart cart = cartRepository.findById(cartId).orElse(null);
        CartProduct cartProduct = new CartProduct(
                null,
                productId,
                productDTO.getName(),
                productDTO.getImageUrl(),
                productDTO.getPrice(),
                1,
                cart
        );
        return Optional.of(cartProductRepository.save(cartProduct));
    }

    @Override
    public Optional<CartProduct> getCartProduct(Long productId, Long cartId) {
        if (productId == null || cartId == null)
            throw new IllegalArgumentException("Product and cart ids must be provided");
        return Optional.ofNullable(cartProductRepository.findByProductIdAndCartId(productId, cartId));
    }

    @Override
    public List<CartProduct> getCartProducts(Long cartId) {
        if (cartId == null)
            throw new IllegalArgumentException("Cart id must be provided");
        return cartProductRepository.findByCartId(cartId);
    }

    @Override
    public Optional<CartProduct> updateCartProduct(CartProduct cartProduct, int quantity) {
        if (cartProduct == null)
            throw new IllegalArgumentException("Cart product must be provided");
        cartProduct.setQuantity(quantity);
        return Optional.of(cartProductRepository.save(cartProduct));
    }

    @Override
    public Optional<CartProduct> updateCartProduct(Long productId, int quantity, Long cartId) {
        return updateCartProduct(getCartProduct(productId, cartId).orElse(null), quantity);
    }

    @Override
    public boolean removeCartProduct(CartProduct cartProduct) {
        if (cartProduct == null)
            return false;
        cartProductRepository.deleteById(cartProduct.getId());
        return true;
    }

    @Override
    public boolean removeCartProduct(Long productId, Long cartId) {
        return removeCartProduct(getCartProduct(productId, cartId).orElse(null));
    }
}
