package com.spring5.recipeapp.services;

import com.spring5.recipeapp.commands.RecipeCommand;
import com.spring5.recipeapp.domain.Recipe;

import java.util.Set;

public interface IRecipeService {

    Set<Recipe> getRecipes();
    Recipe findById(Long l);
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(Long aLong);

    void deleteById(Long id);
}
