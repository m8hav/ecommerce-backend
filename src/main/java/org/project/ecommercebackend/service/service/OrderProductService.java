package org.project.ecommercebackend.service.service;

import org.project.ecommercebackend.dto.model.OrderProductDTO;
import org.project.ecommercebackend.model.CartProduct;
import org.project.ecommercebackend.model.Order;
import org.project.ecommercebackend.model.OrderProduct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
public interface OrderProductService {
//    Optional<OrderProductDTO> createOrderProduct(CartProduct cartProduct, Order order);
    OrderProduct createOrderProduct(CartProduct cartProduct, Order order);

    OrderProduct createOrderProduct(Long productId, String name, String imageUrl, double price, int quantity, Order order);

    List<OrderProduct> getOrderProducts(Long id);
    Optional<OrderProductDTO> getOrderProduct(Long id);
    OrderProduct getOrderProductEntity(Long id);
}
