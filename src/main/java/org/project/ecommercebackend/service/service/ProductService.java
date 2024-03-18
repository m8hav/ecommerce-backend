package org.project.ecommercebackend.service.service;

import org.project.ecommercebackend.dto.model.ProductDTO;
import org.project.ecommercebackend.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
public interface ProductService {
    Optional<ProductDTO> addProduct(ProductDTO productDTO);

    List<ProductDTO> getProducts();

    Optional<ProductDTO> getProduct(Long id);
    Optional<Product> getProductEntity(Long id);

    Optional<ProductDTO> updateProduct(Product product);

    Optional<ProductDTO> updateProduct(ProductDTO productDTO);

    boolean deleteProduct(Long id);
}
