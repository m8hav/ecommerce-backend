package org.project.ecommercebackend.controller;


import org.project.ecommercebackend.dto.model.ProductDTO;
import org.project.ecommercebackend.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/v1")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping(value = "products")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
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

    @GetMapping("/products/search/{searchString}")
    public Set<ProductDTO> getProductsBySearch(@PathVariable String searchString) {
        return productService.getProductsBySearch(searchString);
    }

    @PutMapping("product")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) {
        if (productDTO == null || productDTO.getId() == null) {
            throw new IllegalArgumentException("Product id is required");
        }
        return productService.updateProduct(productDTO).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @DeleteMapping("product/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return true;
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }
}
