package com.spring5.recipeapp.repositories;

import com.spring5.recipeapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ICategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByDescription(String description);
}
