package org.project.ecommercebackend.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.project.ecommercebackend.dto.model.CartDTO;
import org.project.ecommercebackend.dto.model.CartProductDTO;
import org.project.ecommercebackend.model.Cart;
import org.project.ecommercebackend.model.CartProduct;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-29T11:18:28+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
public class CartMapperImpl implements CartMapper {

    @Override
    public CartDTO toCartDTO(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartDTO cartDTO = new CartDTO();

        cartDTO.setId( cart.getId() );
        cartDTO.setUserId( cart.getUserId() );
        cartDTO.setTotal( cart.getTotal() );

        return cartDTO;
    }

    @Override
    public Cart toCart(CartDTO cartDTO) {
        if ( cartDTO == null ) {
            return null;
        }

        Cart cart = new Cart();

        cart.setId( cartDTO.getId() );
        cart.setUserId( cartDTO.getUserId() );
        cart.setTotal( cartDTO.getTotal() );
        cart.setCartProducts( cartProductDTOSetToCartProductSet( cartDTO.getCartProducts() ) );

        return cart;
    }

    protected CartProduct cartProductDTOToCartProduct(CartProductDTO cartProductDTO) {
        if ( cartProductDTO == null ) {
            return null;
        }

        CartProduct cartProduct = new CartProduct();

        cartProduct.setId( cartProductDTO.getId() );
        cartProduct.setProductId( cartProductDTO.getProductId() );
        cartProduct.setName( cartProductDTO.getName() );
        cartProduct.setImageUrl( cartProductDTO.getImageUrl() );
        cartProduct.setPrice( cartProductDTO.getPrice() );
        cartProduct.setQuantity( cartProductDTO.getQuantity() );
        cartProduct.setCart( toCart( cartProductDTO.getCart() ) );

        return cartProduct;
    }

    protected Set<CartProduct> cartProductDTOSetToCartProductSet(Set<CartProductDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<CartProduct> set1 = new LinkedHashSet<CartProduct>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( CartProductDTO cartProductDTO : set ) {
            set1.add( cartProductDTOToCartProduct( cartProductDTO ) );
        }

        return set1;
    }
}
