package org.project.ecommercebackend.mapper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.project.ecommercebackend.dto.model.ProductDTO;
import org.project.ecommercebackend.model.Product;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-29T11:18:29+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toProductDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId( product.getId() );
        productDTO.setName( product.getName() );
        productDTO.setDescription( product.getDescription() );
        productDTO.setPrice( product.getPrice() );
        productDTO.setImageUrl( product.getImageUrl() );
        productDTO.setStock( product.getStock() );

        return productDTO;
    }

    @Override
    public Product toProduct(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( productDTO.getId() );
        product.setName( productDTO.getName() );
        product.setDescription( productDTO.getDescription() );
        product.setPrice( productDTO.getPrice() );
        product.setImageUrl( productDTO.getImageUrl() );
        product.setStock( productDTO.getStock() );

        return product;
    }

    @Override
    public List<ProductDTO> toProductDTOList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( products.size() );
        for ( Product product : products ) {
            list.add( toProductDTO( product ) );
        }

        return list;
    }

    @Override
    public Set<ProductDTO> toProductDTOSet(Set<Product> products) {
        if ( products == null ) {
            return null;
        }

        Set<ProductDTO> set = new LinkedHashSet<ProductDTO>( Math.max( (int) ( products.size() / .75f ) + 1, 16 ) );
        for ( Product product : products ) {
            set.add( toProductDTO( product ) );
        }

        return set;
    }
}
