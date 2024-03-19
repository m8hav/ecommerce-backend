package org.project.ecommercebackend.controller;

import org.project.ecommercebackend.dto.model.OrderDTO;
import org.project.ecommercebackend.dto.request.OrderRequestDTO;
import org.project.ecommercebackend.service.service.CartService;
import org.project.ecommercebackend.service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/v1")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    @Autowired
    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @PostMapping("/order")
    public OrderDTO createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        if (orderRequestDTO == null || orderRequestDTO.getAddress() == null || orderRequestDTO.getPaymentMethod() == null) {
            throw new IllegalArgumentException("Address and payment method are required");
        }
        OrderDTO order = orderService.
                createOrder(orderRequestDTO.getAddress(), orderRequestDTO.getPaymentMethod())
                .orElseThrow(() -> new RuntimeException("Order could not be created"));
//        cartService.clearCart();
        return order;
    }

    @GetMapping("/orders")
    public List<OrderDTO> getUserOrders() {
        return orderService.getUserOrders();
    }

    @GetMapping("/order/{orderId}")
    public OrderDTO getOrderById(@PathVariable Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order id is required");
        }
        return orderService.getOrderById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}