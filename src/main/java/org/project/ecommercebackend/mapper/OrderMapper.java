package org.project.ecommercebackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.project.ecommercebackend.dto.model.OrderDTO;
import org.project.ecommercebackend.model.Order;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
//    @Mapping(target = "orderProducts", ignore = true)
    OrderDTO toOrderDTO(Order order);
    Order toOrder(OrderDTO orderDTO);
    List<OrderDTO> toOrderDTOList(List<Order> orders);
}
