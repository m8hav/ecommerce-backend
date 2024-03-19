package org.project.ecommercebackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.project.ecommercebackend.dto.model.OrderProductDTO;
import org.project.ecommercebackend.model.OrderProduct;

import java.util.List;

@Mapper
public interface OrderProductMapper {
    OrderProductMapper INSTANCE = Mappers.getMapper(OrderProductMapper.class);
    @Mapping(target = "order", ignore = true)
    OrderProductDTO toOrderProductDTO(OrderProduct orderProduct);
    OrderProduct toOrderProduct(OrderProductDTO orderProductDTO);
    List<OrderProductDTO> toOrderProductDTOList(List<OrderProduct> orderProducts);
    List<OrderProductDTO> toOrderProductDTOs(List<OrderProduct> orderProducts);
}
