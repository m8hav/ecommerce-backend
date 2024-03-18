package org.project.ecommercebackend.service.service;

import org.project.ecommercebackend.dto.model.OrderDTO;

import java.util.List;
import java.util.Optional;

//@Service
public interface OrderService {

    Optional<OrderDTO> createOrder(Long userId, String address, String paymentMethod);

    Optional<OrderDTO> createOrder(String address, String paymentMethod);

    List<OrderDTO> getOrdersByUserId(Long userId);

    List<OrderDTO> getUserOrders();

    Optional<OrderDTO> getOrderById(Long orderId);
}