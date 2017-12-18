package com.spring5.recipeapp.converters;

import com.spring5.recipeapp.commands.IngredientCommand;
import com.spring5.recipeapp.commands.UnitOfMeasureCommand;
import com.spring5.recipeapp.domain.Ingredient;
import com.spring5.recipeapp.domain.Recipe;
import com.spring5.recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {
    private static final Recipe recipe = new Recipe();
    private static final BigDecimal amount = new BigDecimal("1");
    private static final String description = "Cheeseburger";
    private static final Long idValue = new Long(1L);
    private static final Long unitOfMeasureId = new Long(2L);
    IngredientToIngredientCommand converter;


    @Before
    public void setUp() throws Exception {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void when_command_object_is_null_converter_returns_null() {
        assertNull(converter.convert(null));
    }

    @Test
    public void when_command_object_is_not_null_but_empty_converter_returns_empty() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(unitOfMeasureId);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(idValue);
        ingredient.setAmount(amount);
        ingredient.setDescription(description);
        ingredient.setUnitOfMeasure(unitOfMeasure);

        //when
        IngredientCommand ingredientCommand = converter.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertNotNull(ingredientCommand.getUnitOfMeasure());
        assertEquals(idValue,ingredientCommand.getId());
        assertEquals(description,ingredientCommand.getDescription());
        assertEquals(amount,ingredientCommand.getAmount());
        assertEquals(unitOfMeasureId,ingredientCommand.getUnitOfMeasure().getId());
    }

    @Test
    public void when_unitOfMeasure_of_ingredient_is_null_converter_sets_it_to_null() throws Exception {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(idValue);
        ingredient.setRecipe(recipe);
        ingredient.setAmount(amount);
        ingredient.setDescription(description);
        ingredient.setUnitOfMeasure(null);
        //when
        IngredientCommand ingredientCommand = converter.convert(ingredient);
        //then
        assertNull(ingredientCommand.getUnitOfMeasure());
        assertEquals(idValue, ingredientCommand.getId());
        assertEquals(amount, ingredientCommand.getAmount());
        assertEquals(description, ingredientCommand.getDescription());
    }

}