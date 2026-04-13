package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
    Optional<Category> findById(Long categoryId);
}