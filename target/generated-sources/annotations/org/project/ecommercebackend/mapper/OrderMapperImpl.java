package org.project.ecommercebackend.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.project.ecommercebackend.dto.model.OrderDTO;
import org.project.ecommercebackend.dto.model.OrderProductDTO;
import org.project.ecommercebackend.model.Order;
import org.project.ecommercebackend.model.OrderProduct;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-19T16:22:30+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDTO toOrderDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId( order.getId() );
        orderDTO.setUserId( order.getUserId() );
        orderDTO.setTotal( order.getTotal() );
        orderDTO.setAddress( order.getAddress() );
        orderDTO.setPaymentMethod( order.getPaymentMethod() );
        orderDTO.setDate( order.getDate() );
        orderDTO.setOrderProducts( orderProductListToOrderProductDTOList( order.getOrderProducts() ) );

        return orderDTO;
    }

    @Override
    public Order toOrder(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        Order order = new Order();

        order.setId( orderDTO.getId() );
        order.setUserId( orderDTO.getUserId() );
        order.setTotal( orderDTO.getTotal() );
        order.setAddress( orderDTO.getAddress() );
        order.setPaymentMethod( orderDTO.getPaymentMethod() );
        order.setDate( orderDTO.getDate() );
        order.setOrderProducts( orderProductDTOListToOrderProductList( orderDTO.getOrderProducts() ) );

        return order;
    }

    @Override
    public List<OrderDTO> toOrderDTOList(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderDTO> list = new ArrayList<OrderDTO>( orders.size() );
        for ( Order order : orders ) {
            list.add( toOrderDTO( order ) );
        }

        return list;
    }

    protected OrderProductDTO orderProductToOrderProductDTO(OrderProduct orderProduct) {
        if ( orderProduct == null ) {
            return null;
        }

        OrderProductDTO orderProductDTO = new OrderProductDTO();

        orderProductDTO.setId( orderProduct.getId() );
        orderProductDTO.setProductId( orderProduct.getProductId() );
        orderProductDTO.setName( orderProduct.getName() );
        orderProductDTO.setImageUrl( orderProduct.getImageUrl() );
        orderProductDTO.setPrice( orderProduct.getPrice() );
        orderProductDTO.setQuantity( orderProduct.getQuantity() );

        return orderProductDTO;
    }

    protected List<OrderProductDTO> orderProductListToOrderProductDTOList(List<OrderProduct> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderProductDTO> list1 = new ArrayList<OrderProductDTO>( list.size() );
        for ( OrderProduct orderProduct : list ) {
            list1.add( orderProductToOrderProductDTO( orderProduct ) );
        }

        return list1;
    }

    protected OrderProduct orderProductDTOToOrderProduct(OrderProductDTO orderProductDTO) {
        if ( orderProductDTO == null ) {
            return null;
        }

        OrderProduct orderProduct = new OrderProduct();

        orderProduct.setId( orderProductDTO.getId() );
        orderProduct.setProductId( orderProductDTO.getProductId() );
        orderProduct.setName( orderProductDTO.getName() );
        orderProduct.setImageUrl( orderProductDTO.getImageUrl() );
        orderProduct.setPrice( orderProductDTO.getPrice() );
        orderProduct.setQuantity( orderProductDTO.getQuantity() );
        orderProduct.setOrder( toOrder( orderProductDTO.getOrder() ) );

        return orderProduct;
    }

    protected List<OrderProduct> orderProductDTOListToOrderProductList(List<OrderProductDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderProduct> list1 = new ArrayList<OrderProduct>( list.size() );
        for ( OrderProductDTO orderProductDTO : list ) {
            list1.add( orderProductDTOToOrderProduct( orderProductDTO ) );
        }

        return list1;
    }
}
