package com.spring5.recipeapp.converters;

import com.spring5.recipeapp.commands.CategoryCommand;
import com.spring5.recipeapp.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    CategoryCommandToCategory converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void when_command_object_is_null_converter_returns_null() {
        assertNull(converter.convert(null));
    }

    @Test
    public void when_command_object_is_not_null_but_empty_converter_returns_empty() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    public void convert() throws Exception {
        Long idValue = 1L;
        String description = "description";

        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(idValue);
        categoryCommand.setDescription(description);

        //when
        Category category = converter.convert(categoryCommand);

        //then
        assertEquals(idValue,category.getId());
        assertEquals(description,category.getDescription());
    }

}