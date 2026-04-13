package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.ProductDTO;
import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.exceptions.CategoryNotFoundException;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import com.codewithmosh.store.services.impl.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private static final Logger logger = LoggerFactory.getLogger(ProductMapper.class);

    ProductRepository productRepository;

    CategoryRepository categoryRepository;

    @Autowired
    ProductMapper(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductDTO toDTO(Product product) {
        if (product == null) return null;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setDescription(product.getDescription());
        productDTO.setName(product.getName());
        if (product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getId());
        }
        return productDTO;
    }

    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) return null;
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        if(productDTO.getPrice()!=null){
            product.setPrice(productDTO.getPrice());
        }
        product.setDescription(productDTO.getDescription());
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()->{
                logger.warn("The category not found with the id {}", productDTO.getCategoryId());
                return new CategoryNotFoundException("Category is not found");
            });
            product.setCategory(category);
        }
        return product;
    }

}
