package org.project.ecommercebackend.service.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.project.ecommercebackend.dto.model.OrderDTO;
import org.project.ecommercebackend.dto.model.OrderProductDTO;
import org.project.ecommercebackend.mapper.OrderMapper;
import org.project.ecommercebackend.mapper.OrderProductMapper;
import org.project.ecommercebackend.model.*;
import org.project.ecommercebackend.repository.OrderRepository;
import org.project.ecommercebackend.service.service.CartService;
import org.project.ecommercebackend.service.service.OrderProductService;
import org.project.ecommercebackend.service.service.OrderService;
import org.project.ecommercebackend.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final OrderProductService orderProductService;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CartService cartService, OrderProductService orderProductService, UserService userService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.orderProductService = orderProductService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Optional<OrderDTO> createOrder(String address, String paymentMethod) {
        Cart cart = cartService.getCartEntity();
        Set<CartProduct> cartProducts = cart.getCartProducts();
        if (cartProducts.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        Order order = new Order(null, cart.getUserId(), 0.0, address, paymentMethod, null, new ArrayList<>());
        Double total = 0.0;
        for (CartProduct cartProduct : cartProducts) {
            OrderProduct orderProduct = orderProductService.createOrderProduct(cartProduct, order);
            if (orderProduct == null) {
                continue;
            }
            order.getOrderProducts().add(orderProduct);
            total += orderProduct.getPrice() * orderProduct.getQuantity();
        }
        order.setTotal(total);
        order = orderRepository.save(order);
//        cartService.clearCart();
        return Optional.of(
                orderDTOWithOrderProductDTOs(
                        OrderMapper.INSTANCE.toOrderDTO(order)
                )
        );
    }

    private OrderDTO orderDTOWithOrderProductDTOs(OrderDTO orderDTO) {
        List<OrderProductDTO> orderProductDTOs =
                OrderProductMapper.INSTANCE.toOrderProductDTOs(
                        orderProductService.getOrderProducts(orderDTO.getId())
                );
        orderDTO.setOrderProducts(orderProductDTOs);
        return orderDTO;
    }

    @Override
    public List<OrderDTO> getUserOrders() {
        User user = userService.getUserEntity();
        List<Order> orders = orderRepository.findByUserId(user.getId());
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderDTOs.add(
                    orderDTOWithOrderProductDTOs(
                            OrderMapper.INSTANCE.toOrderDTO(order)
                    )
            );
        }
        return orderDTOs;
    }

    @Override
    public Optional<OrderDTO> getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new IllegalArgumentException("Order does not exist");
        }
        return Optional.of(
                orderDTOWithOrderProductDTOs(
                        OrderMapper.INSTANCE.toOrderDTO(order)
                )
        );
    }
}