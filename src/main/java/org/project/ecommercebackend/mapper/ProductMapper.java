package org.project.ecommercebackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.project.ecommercebackend.dto.model.ProductDTO;
import org.project.ecommercebackend.model.Product;

import java.util.List;
import java.util.Set;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductDTO toProductDTO(Product product);
    Product toProduct(ProductDTO productDTO);
    List<ProductDTO> toProductDTOList(List<Product> products);
    Set<ProductDTO> toProductDTOSet(Set<Product> products);
}
