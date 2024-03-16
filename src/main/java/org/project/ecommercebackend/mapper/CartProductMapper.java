package org.project.ecommercebackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.project.ecommercebackend.dto.model.CartProductDTO;
import org.project.ecommercebackend.model.CartProduct;

import java.util.List;
import java.util.Set;

@Mapper
public interface CartProductMapper {
    CartProductMapper INSTANCE = Mappers.getMapper(CartProductMapper.class);

    @Mapping(target = "cart", ignore = true)
    CartProductDTO toCartProductDTO(CartProduct cartProduct);
    CartProduct toCartProduct(CartProductDTO cartProductDTO);
    Set<CartProductDTO> toCartProductDTOList(Set<CartProduct> cartProducts);

    Set<CartProductDTO> toCartProductDTOs(List<CartProduct> cartProducts);
}
