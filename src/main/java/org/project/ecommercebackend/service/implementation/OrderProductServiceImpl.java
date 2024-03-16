package org.project.ecommercebackend.service.implementation;

import org.project.ecommercebackend.dto.model.OrderProductDTO;
import org.project.ecommercebackend.dto.model.ProductDTO;
import org.project.ecommercebackend.mapper.OrderProductMapper;
import org.project.ecommercebackend.model.CartProduct;
import org.project.ecommercebackend.model.Order;
import org.project.ecommercebackend.model.OrderProduct;
import org.project.ecommercebackend.repository.OrderProductRepository;
import org.project.ecommercebackend.service.service.OrderProductService;
import org.project.ecommercebackend.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductServiceImpl implements OrderProductService {
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private ProductService productService;
    @Override
    public Optional<OrderProductDTO> createOrderProduct(CartProduct cartProduct, Order order) {
        ProductDTO productDTO = productService.getProduct(cartProduct.getProductId()).orElse(null);
        if (productDTO == null) {
            throw new IllegalArgumentException("Product with this id does not exist");
        }
        if (productDTO.getStock() < cartProduct.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock");
        }
        OrderProduct orderProduct = new OrderProduct(
                null,
                cartProduct.getProductId(),
                cartProduct.getName(),
                cartProduct.getImageUrl(),
                cartProduct.getPrice(),
                cartProduct.getQuantity(),
                order
        );
        orderProduct = orderProductRepository.save(orderProduct);
        productDTO.setStock(productDTO.getStock() - cartProduct.getQuantity());
        productService.updateProduct(productDTO);
        return Optional.ofNullable(OrderProductMapper.INSTANCE.toOrderProductDTO(orderProduct));
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
