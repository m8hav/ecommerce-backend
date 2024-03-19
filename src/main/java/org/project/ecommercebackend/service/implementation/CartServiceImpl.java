package org.project.ecommercebackend.service.implementation;

import org.project.ecommercebackend.dto.model.CartDTO;
import org.project.ecommercebackend.dto.model.CartProductDTO;
import org.project.ecommercebackend.dto.model.ProductDTO;
import org.project.ecommercebackend.mapper.CartMapper;
import org.project.ecommercebackend.mapper.CartProductMapper;
import org.project.ecommercebackend.model.Cart;
import org.project.ecommercebackend.model.CartProduct;
import org.project.ecommercebackend.model.User;
import org.project.ecommercebackend.repository.CartRepository;
import org.project.ecommercebackend.service.service.CartProductService;
import org.project.ecommercebackend.service.service.CartService;
import org.project.ecommercebackend.service.service.ProductService;
import org.project.ecommercebackend.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartProductService cartProductService;
    private final UserService userService;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductService productService, CartProductService cartProductService, UserService userService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartProductService = cartProductService;
        this.userService = userService;
    }

    @Override
    public Optional<CartDTO> createCart(Long userId) {
        if (cartRepository.findByUserId(userId) != null) {
            throw new IllegalArgumentException("Cart for this user already exists");
        }
        CartDTO cartDTO = new CartDTO(null, userId, 0.0, new HashSet<>());
        Cart cart = cartRepository.save(CartMapper.INSTANCE.toCart(cartDTO));
        return Optional.ofNullable(CartMapper.INSTANCE.toCartDTO(cart));
    }

    @Override
    public Optional<CartDTO> createCart() {
        User user = userService.getUserEntity();
        if (cartRepository.findByUserId(user.getId()) != null) {
            throw new IllegalArgumentException("Cart for this user already exists");
        }
        CartDTO cartDTO = new CartDTO(null, user.getId(), 0.0, new HashSet<>());
        Cart cart = cartRepository.save(CartMapper.INSTANCE.toCart(cartDTO));
        return Optional.ofNullable(CartMapper.INSTANCE.toCartDTO(cart));
    }

    private CartDTO cartDTOWithCartProductDTOs(CartDTO cartDTO) {
        Set<CartProductDTO> cartProductDTOs =
                CartProductMapper.INSTANCE.toCartProductDTOs(
                        cartProductService.getCartProducts(cartDTO.getId())
                );
        cartDTO.setCartProducts(cartProductDTOs);
        return cartDTO;
    }

    @Override
    public Optional<CartDTO> getCart(Long userId) {
        refreshCart(userId);
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart for this user does not exist");
        }
        return Optional.of(
                cartDTOWithCartProductDTOs(
                        CartMapper.INSTANCE.toCartDTO(cart)
                )
        );
    }

    @Override
    public Optional<CartDTO> getCart() {
        User user = userService.getUserEntity();
        return getCart(user.getId());
    }

    @Override
    public Cart getCartEntity(Long userId) {
        refreshCart(userId);
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart getCartEntity() {
        User user = userService.getUserEntity();
        refreshCart(user.getId());
        return cartRepository.findByUserId(user.getId());
    }

    private int getProductQuantityInCart(Long userId, Long productId) {
        Cart cart = getCartEntity(userId);
        CartProduct cartProduct = cartProductService.getCartProduct(productId, cart.getId()).orElse(null);
        if (cartProduct == null) {
            return 0;
        }
        Set<CartProduct> cartProducts = cart.getCartProducts();
        for (CartProduct existingCartProduct : cartProducts) {
            if (existingCartProduct.getProductId().equals(productId)) {
                return existingCartProduct.getQuantity();
            }
        }
        throw new RuntimeException("Something went wrong");
    }

    private int getProductQuantityInCart(Long productId) {
        Cart cart = getCartEntity();
        CartProduct cartProduct = cartProductService.getCartProduct(productId, cart.getId()).orElse(null);
        if (cartProduct == null) {
            return 0;
        }
        Set<CartProduct> cartProducts = cart.getCartProducts();
        for (CartProduct existingCartProduct : cartProducts) {
            if (existingCartProduct.getProductId().equals(productId)) {
                return existingCartProduct.getQuantity();
            }
        }
        throw new RuntimeException("Something went wrong");
    }

    @Override
    public Optional<CartDTO> addProductToCart(Long userId, Long productId) {
        refreshCart(userId);
        Cart cart = getCartEntity(userId);
        if (cart == null) {
            cart = CartMapper.INSTANCE.toCart(
                    createCart(userId).orElse(null)
            );
            cart.setCartProducts(new HashSet<>());
        }
        ProductDTO productDTO = productService.getProduct(productId).orElse(null);
        if (productDTO == null) {
            throw new IllegalArgumentException("Product does not exist");
        }
        if (productDTO.getStock() == 0) {
            throw new IllegalArgumentException("Product is out of stock");
        }
        int existingProductQuantityInCart = getProductQuantityInCart(userId, productId);
        if (existingProductQuantityInCart > 0) {
            updateProductQuantityInCart(userId, productId, existingProductQuantityInCart + 1);
            return Optional.of(
                    cartDTOWithCartProductDTOs(CartMapper.INSTANCE.toCartDTO(cart))
            );
        }
        CartProduct cartProduct = cartProductService.createCartProduct(productId, cart.getId()).orElse(null);
        cart.getCartProducts().add(cartProduct);
        cart.setTotal(cart.getTotal() + productDTO.getPrice());
        cart = cartRepository.save(cart);
        return Optional.of(
                cartDTOWithCartProductDTOs(CartMapper.INSTANCE.toCartDTO(cart))
        );
    }

    @Override
    public Optional<CartDTO> addProductToCart(Long productId) {
        refreshCart();
        Cart cart = getCartEntity();
        if (cart == null) {
            cart = CartMapper.INSTANCE.toCart(
                    createCart().orElse(null)
            );
            cart.setCartProducts(new HashSet<>());
        }
        ProductDTO productDTO = productService.getProduct(productId).orElse(null);
        if (productDTO == null) {
            throw new IllegalArgumentException("Product does not exist");
        }
        if (productDTO.getStock() == 0) {
            throw new IllegalArgumentException("Product is out of stock");
        }
        int existingProductQuantityInCart = getProductQuantityInCart(productId);
        if (existingProductQuantityInCart > 0) {
            updateProductQuantityInCart(productId, existingProductQuantityInCart + 1);
            return Optional.of(
                    cartDTOWithCartProductDTOs(CartMapper.INSTANCE.toCartDTO(cart))
            );
        }
        CartProduct cartProduct = cartProductService.createCartProduct(productId, cart.getId()).orElse(null);
        cart.getCartProducts().add(cartProduct);
        cart.setTotal(cart.getTotal() + productDTO.getPrice());
        cart = cartRepository.save(cart);
        return Optional.of(
                cartDTOWithCartProductDTOs(CartMapper.INSTANCE.toCartDTO(cart))
        );
    }

    @Override
    public Optional<CartDTO> updateProductQuantityInCart(Long userId, Long productId, int quantity) {
        refreshCart(userId);
        Cart cart = getCartEntity(userId);
        if (cart == null) {
            createCart(userId);
        }
        ProductDTO productDTO = productService.getProduct(productId).orElse(null);
        if (productDTO == null) {
            throw new IllegalArgumentException("Product does not exist");
        }
        if (productDTO.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }
        if (quantity <= 0) {
            return removeProductFromCart(userId, productId);
        }
        Set<CartProduct> cartProducts = cart.getCartProducts();
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProductId().equals(productId)) {
                cart.setTotal(cart.getTotal() - cartProduct.getPrice() * cartProduct.getQuantity() + cartProduct.getPrice() * quantity);
                cartProduct.setQuantity(quantity);
                cartProductService.updateCartProduct(cartProduct, quantity);
                cart = cartRepository.save(cart);
                return Optional.of(
                        cartDTOWithCartProductDTOs(CartMapper.INSTANCE.toCartDTO(cart))
                );
            }
        }
        throw new IllegalArgumentException("Product does not exist in cart");
    }

    @Override
    public Optional<CartDTO> updateProductQuantityInCart(Long productId, int quantity) {
        refreshCart();
        Cart cart = getCartEntity();
        if (cart == null) {
            createCart();
        }
        ProductDTO productDTO = productService.getProduct(productId).orElse(null);
        if (productDTO == null) {
            throw new IllegalArgumentException("Product does not exist");
        }
        if (productDTO.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }
        if (quantity <= 0) {
            return removeProductFromCart(productId);
        }
        Set<CartProduct> cartProducts = cart.getCartProducts();
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProductId().equals(productId)) {
                cart.setTotal(cart.getTotal() - cartProduct.getPrice() * cartProduct.getQuantity() + cartProduct.getPrice() * quantity);
                cartProduct.setQuantity(quantity);
                cartProductService.updateCartProduct(cartProduct, quantity);
                cart = cartRepository.save(cart);
                return Optional.of(
                        cartDTOWithCartProductDTOs(CartMapper.INSTANCE.toCartDTO(cart))
                );
            }
        }
        throw new IllegalArgumentException("Product does not exist in cart");
    }

    @Override
    public Optional<CartDTO> removeProductFromCart(Long userId, Long productId) {
        refreshCart(userId);
        Cart cart = getCartEntity(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart for this user does not exist");
        }
        Set<CartProduct> cartProducts = cart.getCartProducts();
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProductId().equals(productId)) {
                cartProducts.remove(cartProduct);
                cart.setCartProducts(cartProducts);
                cart.setTotal(cart.getTotal() - cartProduct.getPrice() * cartProduct.getQuantity());
                cartProductService.removeCartProduct(cartProduct);
                cart = cartRepository.save(cart);
                return Optional.of(
                        cartDTOWithCartProductDTOs(CartMapper.INSTANCE.toCartDTO(cart))
                );
            }
        }
        throw new IllegalArgumentException("Product does not exist in cart");
    }

    @Override
    public Optional<CartDTO> removeProductFromCart(Long productId) {
        refreshCart();
        Cart cart = getCartEntity();
        if (cart == null) {
            throw new IllegalArgumentException("Cart for this user does not exist");
        }
        Set<CartProduct> cartProducts = cart.getCartProducts();
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProductId().equals(productId)) {
                cartProducts.remove(cartProduct);
                cart.setCartProducts(cartProducts);
                cart.setTotal(cart.getTotal() - cartProduct.getPrice() * cartProduct.getQuantity());
                cartProductService.removeCartProduct(cartProduct);
                cart = cartRepository.save(cart);
                return Optional.of(
                        cartDTOWithCartProductDTOs(CartMapper.INSTANCE.toCartDTO(cart))
                );
            }
        }
        throw new IllegalArgumentException("Product does not exist in cart");
    }

    @Override
    public boolean clearCart(Long userId) {
        Cart cart = getCartEntity(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart for this user does not exist");
        }
        Set<CartProduct> cartProducts = cart.getCartProducts();
        for (CartProduct cartProduct : cartProducts) {
            removeProductFromCart(userId, cartProduct.getProductId());
        }
        cartProducts.clear();
        cart.setTotal(0.0);
        return cartRepository.save(cart).getTotal() == 0.0;
    }

    @Override
    public boolean clearCart() {
        Cart cart = getCartEntity();
        if (cart == null) {
            throw new IllegalArgumentException("Cart for this user does not exist");
        }
        Set<CartProduct> cartProducts = cart.getCartProducts();
        for (CartProduct cartProduct : cartProducts) {
            removeProductFromCart(cartProduct.getProductId());
        }
        cartProducts.clear();
        cart.setTotal(0.0);
        return cartRepository.save(cart).getTotal() == 0.0;
    }

    @Override
    public void refreshCart(Long userId) {
        if (userService.getUserById(userId).isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            return;
        }
        Set<CartProduct> cartProducts = cart.getCartProducts();
        Double total = 0.0;
        for (CartProduct cartProduct : cartProducts) {
            ProductDTO productDTO = productService.getProduct(cartProduct.getProductId()).orElse(null);
            if (productDTO == null) {
                cartProducts.remove(cartProduct);
            } else {
                cartProduct.setPrice(productDTO.getPrice());
                total += productDTO.getPrice() * cartProduct.getQuantity();
            }
        }
        cart.setTotal(total);
        cartRepository.save(cart);
    }

    @Override
    public void refreshCart() {
        User user = userService.getUserEntity();
        if (userService.getUserById(user.getId()).isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null) {
            return;
        }
        Set<CartProduct> cartProducts = cart.getCartProducts();
        Double total = 0.0;
        for (CartProduct cartProduct : cartProducts) {
            ProductDTO productDTO = productService.getProduct(cartProduct.getProductId()).orElse(null);
            if (productDTO == null) {
                cartProducts.remove(cartProduct);
            } else {
                cartProduct.setPrice(productDTO.getPrice());
                total += productDTO.getPrice() * cartProduct.getQuantity();
            }
        }
        cart.setTotal(total);
        cartRepository.save(cart);
    }
}