package org.project.ecommercebackend.service.implementation;

import org.project.ecommercebackend.dto.model.OrderProductDTO;
import org.project.ecommercebackend.mapper.OrderProductMapper;
import org.project.ecommercebackend.model.CartProduct;
import org.project.ecommercebackend.model.Order;
import org.project.ecommercebackend.model.OrderProduct;
import org.project.ecommercebackend.model.Product;
import org.project.ecommercebackend.repository.OrderProductRepository;
import org.project.ecommercebackend.service.service.OrderProductService;
import org.project.ecommercebackend.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final ProductService productService;

    @Autowired
    public OrderProductServiceImpl(OrderProductRepository orderProductRepository, ProductService productService) {
        this.orderProductRepository = orderProductRepository;
        this.productService = productService;
    }


    @Override
    public OrderProduct createOrderProduct(CartProduct cartProduct, Order order) {
        return createOrderProduct(
                cartProduct.getProductId(),
                cartProduct.getName(),
                cartProduct.getImageUrl(),
                cartProduct.getPrice(),
                cartProduct.getQuantity(),
                order
        );
    }

    @Override
    public OrderProduct createOrderProduct(Long productId, String name, String imageUrl, double price, int quantity, Order order) {
        Product product = productService.getProductEntity(productId).orElse(null);
        if (product == null) {
            throw new IllegalArgumentException("Product with this id does not exist");
        }
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }
        OrderProduct orderProduct = new OrderProduct(
                null,
                productId,
                name,
                imageUrl,
                price,
                quantity,
                order
        );
        product.setStock(product.getStock() - quantity);
        productService.updateProduct(product);
        return orderProduct;
    }

    @Override
    public List<OrderProduct> getOrderProducts(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Order id must be provided");
        return orderProductRepository.findByOrderId(id);
    }

    @Override
    public Optional<OrderProductDTO> getOrderProduct(Long id) {
        OrderProduct orderProduct = orderProductRepository.findById(id).orElse(null);
        if (orderProduct == null) {
            throw new IllegalArgumentException("Order product does not exist");
        }
        return Optional.ofNullable(OrderProductMapper.INSTANCE.toOrderProductDTO(orderProduct));
    }

    @Override
    public OrderProduct getOrderProductEntity(Long id) {
        return orderProductRepository.findById(id).orElse(null);
    }
}
