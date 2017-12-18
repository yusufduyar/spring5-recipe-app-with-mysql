package com.spring5.recipeapp.controllers;

import com.spring5.recipeapp.commands.RecipeCommand;
import com.spring5.recipeapp.services.IImageService;
import com.spring5.recipeapp.services.IRecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

public class ImageControllerTest {
    @Mock
    IImageService imageService;

    @Mock
    IRecipeService recipeService;

    ImageController imageController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void getImageForm_returns_a_form_to_upload_image() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/imageuploadform"));

        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void handleImagePostTest() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "test.txt", "text/plain", "hello world".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/show"));
               // .andExpect(header().string("Location","/recipe/1/show"));

        verify(imageService,times(1)).saveImageFile(anyLong(),any());
    }

    @Test
    public void renderImageFromDB_gets_image() throws Exception{
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        String imageText = "image as text";
        Byte[] imageBytes = new Byte[imageText.getBytes().length];

        int i =0;
        for(byte b: imageText.getBytes()){
            imageBytes[i++] = b;
        }

        command.setImage(imageBytes);
        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //then
        byte[] responseInBytes = response.getContentAsByteArray();
        assertEquals(imageText.getBytes().length,responseInBytes.length);
    }

    @Test
    public void getImage_throws_numberFormatException_when_recipeId_is_not_a_number() throws Exception{
        mockMvc.perform(get("/recipe/asd/image"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

}