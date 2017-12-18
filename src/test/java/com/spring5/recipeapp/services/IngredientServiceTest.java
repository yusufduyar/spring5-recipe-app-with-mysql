package com.spring5.recipeapp.services;

import com.spring5.recipeapp.commands.IngredientCommand;
import com.spring5.recipeapp.converters.IngredientCommandToIngredient;
import com.spring5.recipeapp.converters.IngredientToIngredientCommand;
import com.spring5.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring5.recipeapp.domain.Ingredient;
import com.spring5.recipeapp.domain.Recipe;
import com.spring5.recipeapp.repositories.IIngredientRepository;
import com.spring5.recipeapp.repositories.IRecipeRepository;
import com.spring5.recipeapp.repositories.IUnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IngredientServiceTest {
    @Mock
    IRecipeRepository recipeRepository;

    @Mock
    IIngredientRepository ingredientRepository;

    IngredientService ingredientService;
    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    IUnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientService(ingredientToIngredientCommand, recipeRepository,
                unitOfMeasureToUnitOfMeasureCommand, ingredientCommandToIngredient, unitOfMeasureRepository, ingredientRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(2L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);

        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 2L);

        //then
        assertEquals(Long.valueOf(2L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void saveIngredientCommandTest() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        ingredientCommand.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(1L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);
        //when
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //then
        assertEquals(Long.valueOf(1L), savedIngredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any());
    }

    @Test
    public void deleteIngredient_should_delete_ingredient_by_id() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);

        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteById(1L,3L);

        //then
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}