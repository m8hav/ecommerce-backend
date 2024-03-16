package org.project.ecommercebackend.controller;


import org.project.ecommercebackend.dto.model.ProductDTO;
import org.project.ecommercebackend.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/products")
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO).orElseThrow(() -> new IllegalArgumentException("Product info is required"));
    }

    @GetMapping("/products")
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/product/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProduct(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @PutMapping("/product")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) {
        if (productDTO == null || productDTO.getId() == null) {
            throw new IllegalArgumentException("Product id is required");
        }
        return productService.updateProduct(productDTO).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @DeleteMapping("/product/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return true;
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }
}
