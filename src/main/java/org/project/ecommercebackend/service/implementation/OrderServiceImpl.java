package org.project.ecommercebackend.service.implementation;

import org.project.ecommercebackend.dto.model.CartDTO;
import org.project.ecommercebackend.dto.model.CartProductDTO;
import org.project.ecommercebackend.dto.model.OrderDTO;
import org.project.ecommercebackend.dto.model.OrderProductDTO;
import org.project.ecommercebackend.mapper.CartProductMapper;
import org.project.ecommercebackend.mapper.OrderMapper;
import org.project.ecommercebackend.mapper.OrderProductMapper;
import org.project.ecommercebackend.model.CartProduct;
import org.project.ecommercebackend.model.Order;
import org.project.ecommercebackend.model.OrderProduct;
import org.project.ecommercebackend.repository.OrderRepository;
import org.project.ecommercebackend.service.service.CartService;
import org.project.ecommercebackend.service.service.OrderProductService;
import org.project.ecommercebackend.service.service.OrderService;
import org.project.ecommercebackend.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderProductService orderProductService;

    @Override
    public Optional<OrderDTO> createOrder(Long userId, String address, String paymentMethod) {
        Set<CartProduct> cartProducts = cartService.getCartEntity(userId).getCartProducts();
        if (cartProducts.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        Order order = new Order(null, userId, 0.0, address, paymentMethod, null, new ArrayList<>());
        order = orderRepository.save(order);
        Double total = 0.0;
        for (CartProduct cartProduct : cartProducts) {
            OrderProduct orderProduct = orderProductService.getOrderProductEntity(cartProduct.getId());
            if (orderProduct == null) {
                Optional<OrderProductDTO> orderProductDTO = orderProductService.createOrderProduct(cartProduct, order);
                if (orderProductDTO.isEmpty()) {
                    continue;
                }
                orderProduct = OrderProductMapper.INSTANCE.toOrderProduct(orderProductDTO.get());
            }
            order.getOrderProducts().add(orderProduct);
            total += orderProduct.getPrice() * orderProduct.getQuantity();
            System.out.println(total);
        }
        order.setTotal(total);
        order = orderRepository.save(order);
        cartService.clearCart(userId);
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
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
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