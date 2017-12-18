package com.spring5.recipeapp.converters;

import com.spring5.recipeapp.commands.UnitOfMeasureCommand;
import com.spring5.recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void when_command_object_is_null_converter_returns_null() {
        assertNull(converter.convert(null));
    }

    @Test
    public void when_command_object_is_not_null_but_empty_converter_returns_empty() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() throws Exception {
        Long idValue = 1L;
        String description  = "description";

        //given
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(idValue);
        unitOfMeasureCommand.setDescription(description);

        //when
        UnitOfMeasure unitOfMeasure = converter.convert(unitOfMeasureCommand);

        //then
        assertNotNull(unitOfMeasure);
        assertEquals(idValue,unitOfMeasure.getId());
        assertEquals(description,unitOfMeasure.getDescription());

    }
}