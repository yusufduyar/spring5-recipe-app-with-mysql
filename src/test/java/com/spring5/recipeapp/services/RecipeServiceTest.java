package com.spring5.recipeapp.services;

import com.spring5.recipeapp.converters.RecipeCommandToRecipe;
import com.spring5.recipeapp.converters.RecipeToRecipeCommand;
import com.spring5.recipeapp.domain.Recipe;
import com.spring5.recipeapp.exceptions.NotFoundException;
import com.spring5.recipeapp.repositories.IRecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {
    RecipeService recipeService;

    @Mock
    IRecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeService(recipeRepository, recipeToRecipeCommand, recipeCommandToRecipe);
    }

    @Test
    public void getRecipeByIdTest() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe returnedRecipe = recipeService.findById(1L);

        assertNotNull("Null recipe returned",returnedRecipe);
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeById_throws_NotFoundException() throws Exception{
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe returnedRecipe = recipeService.findById(1L);
    }

    @Test(expected = NumberFormatException.class)
    public void getRecipeById_throws_NumberFormatException_when_id_is_not_a_number() throws Exception{
        Recipe returnedRecipe = recipeService.findById(Long.valueOf("asd"));
    }

    @Test
    public void getRecipesTest() throws Exception {
        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(),1);
        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    public void recipe_service_deletes_by_id() throws Exception{
        //given
        Long idValue = Long.valueOf(1L);

        //when
        recipeService.deleteById(idValue);

        //then
        verify(recipeRepository,times(1)).deleteById(anyLong());
    }
}