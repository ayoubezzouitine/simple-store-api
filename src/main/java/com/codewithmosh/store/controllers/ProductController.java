package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.ProductDTO;
import com.codewithmosh.store.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getListProduct() {
        List<ProductDTO> allProducts = productService.getAllProducts();
        return allProducts.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(allProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return productService
                .getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(@Validated @RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,@Validated @RequestBody ProductDTO productDTO) {
        ProductDTO updateProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if(productService.getProductById(id).isPresent()){
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getListProductByCategoryId(@PathVariable Long categoryId) {
        List<ProductDTO> listProductByCategoryId = productService.getListProductByCategoryId(categoryId);
       return  listProductByCategoryId.isEmpty() ? ResponseEntity.noContent().build() :ResponseEntity.noContent().build();
    }

}
