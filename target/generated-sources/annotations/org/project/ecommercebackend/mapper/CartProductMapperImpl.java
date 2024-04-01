package org.project.ecommercebackend.mapper;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.project.ecommercebackend.dto.model.CartDTO;
import org.project.ecommercebackend.dto.model.CartProductDTO;
import org.project.ecommercebackend.model.Cart;
import org.project.ecommercebackend.model.CartProduct;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-29T11:18:29+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
public class CartProductMapperImpl implements CartProductMapper {

    @Override
    public CartProductDTO toCartProductDTO(CartProduct cartProduct) {
        if ( cartProduct == null ) {
            return null;
        }

        CartProductDTO cartProductDTO = new CartProductDTO();

        cartProductDTO.setId( cartProduct.getId() );
        cartProductDTO.setProductId( cartProduct.getProductId() );
        cartProductDTO.setName( cartProduct.getName() );
        cartProductDTO.setImageUrl( cartProduct.getImageUrl() );
        cartProductDTO.setPrice( cartProduct.getPrice() );
        cartProductDTO.setQuantity( cartProduct.getQuantity() );

        return cartProductDTO;
    }

    @Override
    public CartProduct toCartProduct(CartProductDTO cartProductDTO) {
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
        cartProduct.setCart( cartDTOToCart( cartProductDTO.getCart() ) );

        return cartProduct;
    }

    @Override
    public Set<CartProductDTO> toCartProductDTOList(Set<CartProduct> cartProducts) {
        if ( cartProducts == null ) {
            return null;
        }

        Set<CartProductDTO> set = new LinkedHashSet<CartProductDTO>( Math.max( (int) ( cartProducts.size() / .75f ) + 1, 16 ) );
        for ( CartProduct cartProduct : cartProducts ) {
            set.add( toCartProductDTO( cartProduct ) );
        }

        return set;
    }

    @Override
    public Set<CartProductDTO> toCartProductDTOs(List<CartProduct> cartProducts) {
        if ( cartProducts == null ) {
            return null;
        }

        Set<CartProductDTO> set = new LinkedHashSet<CartProductDTO>( Math.max( (int) ( cartProducts.size() / .75f ) + 1, 16 ) );
        for ( CartProduct cartProduct : cartProducts ) {
            set.add( toCartProductDTO( cartProduct ) );
        }

        return set;
    }

    protected Set<CartProduct> cartProductDTOSetToCartProductSet(Set<CartProductDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<CartProduct> set1 = new LinkedHashSet<CartProduct>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( CartProductDTO cartProductDTO : set ) {
            set1.add( toCartProduct( cartProductDTO ) );
        }

        return set1;
    }

    protected Cart cartDTOToCart(CartDTO cartDTO) {
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
}
