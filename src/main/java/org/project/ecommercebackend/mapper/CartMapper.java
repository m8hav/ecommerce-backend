package org.project.ecommercebackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.project.ecommercebackend.dto.model.CartDTO;
import org.project.ecommercebackend.model.Cart;

import java.util.List;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "cartProducts", ignore = true)
    CartDTO toCartDTO(Cart cart);
    Cart toCart(CartDTO cartDTO);
}
