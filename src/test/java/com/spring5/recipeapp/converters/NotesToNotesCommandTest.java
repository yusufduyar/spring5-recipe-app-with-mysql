package com.spring5.recipeapp.converters;

import com.spring5.recipeapp.commands.NotesCommand;
import com.spring5.recipeapp.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {
    NotesToNotesCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void when_command_object_is_null_converter_returns_null() {
        assertNull(converter.convert(null));
    }

    @Test
    public void when_command_object_is_not_null_but_empty_converter_returns_empty() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    public void convert() throws Exception {
        Long idValue = new Long(1);
        String recipeNotes = "Recipe Notes";

        //given
        Notes notes = new Notes();
        notes.setId(idValue);
        notes.setRecipeNotes(recipeNotes);

        //when
        NotesCommand notesCommand = converter.convert(notes);

        //then
        assertEquals(idValue,notesCommand.getId());
        assertEquals(recipeNotes,notesCommand.getRecipeNotes());
    }

}