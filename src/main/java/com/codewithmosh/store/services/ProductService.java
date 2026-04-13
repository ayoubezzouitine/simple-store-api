package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.ProductDTO;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    Optional<ProductDTO> getProductById(Long id);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);

    List<ProductDTO> getListProductByCategoryId(Long categoryId);
}
