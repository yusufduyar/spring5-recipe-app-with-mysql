package com.spring5.recipeapp.repositories;

import com.spring5.recipeapp.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IIngredientRepository extends CrudRepository<Ingredient, Long> {
}
