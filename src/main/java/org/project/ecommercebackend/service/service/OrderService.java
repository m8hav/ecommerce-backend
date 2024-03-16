package org.project.ecommercebackend.service.service;

import org.project.ecommercebackend.dto.model.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface OrderService {

    Optional<OrderDTO> createOrder(Long userId, String address, String paymentMethod);
    List<OrderDTO> getOrdersByUserId(Long userId);

    Optional<OrderDTO> getOrderById(Long orderId);
}