package org.project.ecommercebackend.controller;

import org.project.ecommercebackend.dto.model.OrderDTO;
import org.project.ecommercebackend.dto.request.OrderRequestDTO;
import org.project.ecommercebackend.service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public OrderDTO checkout(@RequestBody OrderRequestDTO orderRequestDTO) {
        if (orderRequestDTO == null || orderRequestDTO.getUserId() == null || orderRequestDTO.getAddress() == null || orderRequestDTO.getPaymentMethod() == null) {
            throw new IllegalArgumentException("User id, address and payment method are required");
        }
        return orderService.
                createOrder(orderRequestDTO.getUserId(), orderRequestDTO.getAddress(), orderRequestDTO.getPaymentMethod())
                .orElseThrow(() -> new RuntimeException("Order could not be created"));
    }

    @GetMapping("/orders/user/{userId}")
    public List<OrderDTO> getOrdersByUserId(@PathVariable Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id is required");
        }
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/order/{orderId}")
    public OrderDTO getOrderById(@PathVariable Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order id is required");
        }
        return orderService.getOrderById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}