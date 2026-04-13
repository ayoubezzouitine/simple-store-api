package com.codewithmosh.store.services.impl;

import com.codewithmosh.store.dtos.ProductDTO;
import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.exceptions.CategoryNotFoundException;
import com.codewithmosh.store.exceptions.ProductNotFoundException;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import com.codewithmosh.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    ProductRepository productRepository;
    ProductMapper productMapper;

    CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    /**
     * @return
     */
    @Override
    public List<ProductDTO> getAllProducts() {
        logger.info("Fetching all products" );

        List<ProductDTO> listProductDto = StreamSupport
                .stream(productRepository.findAll().spliterator(), false)
                .map(productMapper::toDTO).collect(Collectors.toList());
        logger.debug("Total products fetched: {}", listProductDto.size());
        return listProductDto;

    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        logger.info("Searching Product by {}", id);
        Product product = productRepository.findById(id).orElseThrow(() -> {
            logger.warn("Product not found with id ={}", id);
            return new RuntimeException("Product not found");
        });

        return Optional.of(productMapper.toDTO(product));

    }

    /**
     * @param productDTO
     * @return
     */
    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        logger.info("Create Product DTO {}", productDTO);
        productDTO.setId(null);
        Product product = productMapper.toEntity(productDTO);
        try {
            Product savedProduct = productRepository.save(product);
            return productMapper.toDTO(savedProduct);
        }catch (Exception exception){
            logger.error("Error while creating product: {}",product, exception);
        }
        return null;
    }

    /**
     * @param id
     * @param productDTO
     * @return
     */
    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        logger.info("Updading product by id {} ", id );
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()->{
            logger.warn("Category not found under the id {}", productDTO.getCategoryId());
            return new CategoryNotFoundException("Category not found");
        });
        product.setCategory(category);
        try {
            return productMapper.toDTO(productRepository.save(product));
        }catch (Exception exception){
            logger.error("Error while updating product: {} ",product, exception);
        }
     return null;
    }


    /**
     * @param id
     */
    @Override
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)){
            logger.warn("Trying to delete non-existing product id={}", id);
            throw new ProductNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
        logger.info("Product deleted successfully id={}" , id );

    }

    /**
     * @param categoryId
     * @return
     */
    @Override
    public List<ProductDTO> getListProductByCategoryId(Long categoryId) {
        logger.info("Fetching list products by category id {}", categoryId);
        return productRepository
                .getListProductByCategoryId(categoryId)
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }
}
