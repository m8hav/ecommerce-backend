package org.project.ecommercebackend.service.implementation;

import org.project.ecommercebackend.dto.model.ProductDTO;
import org.project.ecommercebackend.mapper.ProductMapper;
import org.project.ecommercebackend.model.Product;
import org.project.ecommercebackend.repository.ProductRepository;
import org.project.ecommercebackend.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private boolean checkIfProductValid(ProductDTO productDTO) {
        return productDTO != null
                && productDTO.getName() != null
                && productDTO.getDescription() != null
                && productDTO.getPrice() != 0
                && productDTO.getImageUrl() != null
                && productDTO.getStock() != 0;
    }

    @Override
    public Optional<ProductDTO> addProduct(ProductDTO productDTO) {
        if (!checkIfProductValid(productDTO)) {
            throw new IllegalArgumentException("All product fields must be provided");
        }
        Product product = ProductMapper.INSTANCE.toProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        return Optional.of(ProductMapper.INSTANCE.toProductDTO(savedProduct));
    }

    @Override
    public List<ProductDTO> getProducts() {
        List<Product> products = productRepository.findAll();
        return ProductMapper.INSTANCE.toProductDTOList(products);
    }

    @Override
    public Optional<ProductDTO> getProduct(Long id) {
        return getProductEntity(id).map(ProductMapper.INSTANCE::toProductDTO);
    }

    @Override
    public Optional<Product> getProductEntity(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<ProductDTO> updateProduct(Product product) {
        return Optional.of(ProductMapper.INSTANCE.toProductDTO(productRepository.save(product)));
    }

    @Override
    public Optional<ProductDTO> updateProduct(ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productDTO.getId()).orElse(null);
        if(existingProduct == null) {
            throw new IllegalArgumentException("Product with this id does not exist");
        }
        Product product = ProductMapper.INSTANCE.toProduct(
                ProductMapper.INSTANCE.toProductDTO(existingProduct)
                        .updateProduct(productDTO)
        );
        return updateProduct(product);
    }

    @Override
    public boolean deleteProduct(Long id) {
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}