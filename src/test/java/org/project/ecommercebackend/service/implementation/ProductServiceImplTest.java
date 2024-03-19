package org.project.ecommercebackend.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.project.ecommercebackend.dto.model.ProductDTO;
import org.project.ecommercebackend.mapper.ProductMapper;
import org.project.ecommercebackend.model.Product;
import org.project.ecommercebackend.repository.ProductRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should add product successfully")
    void addProductSuccessfully() {
        ProductDTO productDTO = new ProductDTO(null, "Test Product", "Test Description", 100.0, "test.jpg", 10);
        Product product = ProductMapper.INSTANCE.toProduct(productDTO);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Optional<ProductDTO> result = productService.addProduct(productDTO);
        assertEquals(productDTO.getName(), result.get().getName());
        assertEquals(productDTO.getDescription(), result.get().getDescription());
        assertEquals(productDTO.getPrice(), result.get().getPrice());
        assertEquals(productDTO.getStock(), result.get().getStock());
        assertEquals(productDTO.getImageUrl(), result.get().getImageUrl());
    }

    @Test
    @DisplayName("Should throw exception when adding invalid product")
    void addInvalidProduct() {
        ProductDTO productDTO = new ProductDTO(null, null, "Test Description", 100.0, "test.jpg", 10);
        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(productDTO));
    }

    @Test
    @DisplayName("Should return product successfully")
    void getProductSuccessfully() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));
        Optional<ProductDTO> result = productService.getProduct(1L);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional when product not found")
    void getInvalidProduct() {
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertTrue(productService.getProduct(1L).isEmpty());
    }

    @Test
    @DisplayName("Should return product entity successfully")
    void getProductEntity() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));
        Optional<Product> result = productService.getProductEntity(1L);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional when product not found")
    void getInvalidProductEntity() {
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Optional<Product> result = productService.getProductEntity(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return empty set when matching products not found")
    void getProductsBySearchUnsuccessfully() {
        when(productRepository.findByNameContainingIgnoreCase("Test")).thenReturn(new HashSet<>());
        assertTrue(productService.getProductsBySearch("Test").isEmpty());
    }

    @Test
    @DisplayName("Should return set of products when matching products found")
    void getProductsBySearchSuccessfully() {
        Set<Product> products = new HashSet<>();
        products.add(new Product());
        when(productRepository.findByNameContainingIgnoreCase("Test")).thenReturn(products);
        assertFalse(productService.getProductsBySearch("Test").isEmpty());
    }

    @Test
    @DisplayName("Should throw error when product not found")
    void updateInvalidProduct() {
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(new ProductDTO(1L, "Test Product", "Test Description", 100.0, "test.jpg", 10)).isEmpty());
    }

    @Test
    @DisplayName("Should return updated product successfully")
    void updateProductSuccessfully() {
        ProductDTO productDTO = new ProductDTO(1L, "Test Product", "Test Description", 100.0, "test.jpg", 10);
        Product product = ProductMapper.INSTANCE.toProduct(productDTO);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Optional<ProductDTO> result = productService.updateProduct(productDTO);
        assertEquals(productDTO.getName(), result.get().getName());
        assertEquals(productDTO.getDescription(), result.get().getDescription());
        assertEquals(productDTO.getPrice(), result.get().getPrice());
        assertEquals(productDTO.getStock(), result.get().getStock());
        assertEquals(productDTO.getImageUrl(), result.get().getImageUrl());
    }

    @Test
    @DisplayName("Should return false when product not found")
    void deleteInvalidProduct() {
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertFalse(productService.deleteProduct(1L));
    }

    @Test
    @DisplayName("Should return true when deleted product successfully")
    void deleteProductSuccessfully() {
        when(productRepository.existsById(1L)).thenReturn(true);
        assertTrue(productService.deleteProduct(1L));
    }
}