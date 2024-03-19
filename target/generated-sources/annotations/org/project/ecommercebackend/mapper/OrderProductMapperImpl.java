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
    date = "2024-03-19T13:51:53+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
public class OrderProductMapperImpl implements OrderProductMapper {

    @Override
    public OrderProductDTO toOrderProductDTO(OrderProduct orderProduct) {
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

    @Override
    public OrderProduct toOrderProduct(OrderProductDTO orderProductDTO) {
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
        orderProduct.setOrder( orderDTOToOrder( orderProductDTO.getOrder() ) );

        return orderProduct;
    }

    @Override
    public List<OrderProductDTO> toOrderProductDTOList(List<OrderProduct> orderProducts) {
        if ( orderProducts == null ) {
            return null;
        }

        List<OrderProductDTO> list = new ArrayList<OrderProductDTO>( orderProducts.size() );
        for ( OrderProduct orderProduct : orderProducts ) {
            list.add( toOrderProductDTO( orderProduct ) );
        }

        return list;
    }

    @Override
    public List<OrderProductDTO> toOrderProductDTOs(List<OrderProduct> orderProducts) {
        if ( orderProducts == null ) {
            return null;
        }

        List<OrderProductDTO> list = new ArrayList<OrderProductDTO>( orderProducts.size() );
        for ( OrderProduct orderProduct : orderProducts ) {
            list.add( toOrderProductDTO( orderProduct ) );
        }

        return list;
    }

    protected List<OrderProduct> orderProductDTOListToOrderProductList(List<OrderProductDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderProduct> list1 = new ArrayList<OrderProduct>( list.size() );
        for ( OrderProductDTO orderProductDTO : list ) {
            list1.add( toOrderProduct( orderProductDTO ) );
        }

        return list1;
    }

    protected Order orderDTOToOrder(OrderDTO orderDTO) {
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
}
