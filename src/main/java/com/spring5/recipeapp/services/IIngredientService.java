package com.spring5.recipeapp.services;

import com.spring5.recipeapp.commands.IngredientCommand;

public interface IIngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId,Long ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteById(Long recipeId,Long ingredientId);
}
