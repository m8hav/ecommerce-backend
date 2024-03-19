package org.project.ecommercebackend.service.service;

import org.project.ecommercebackend.dto.model.OrderDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<OrderDTO> createOrder(String address, String paymentMethod);

    List<OrderDTO> getUserOrders();

    Optional<OrderDTO> getOrderById(Long orderId);
}