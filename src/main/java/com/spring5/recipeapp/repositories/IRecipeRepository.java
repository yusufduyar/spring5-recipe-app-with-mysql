package com.spring5.recipeapp.repositories;

import com.spring5.recipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface IRecipeRepository extends CrudRepository<Recipe,Long> {
}
