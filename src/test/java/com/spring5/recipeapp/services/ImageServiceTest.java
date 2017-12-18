package com.spring5.recipeapp.services;

import com.spring5.recipeapp.domain.Recipe;
import com.spring5.recipeapp.repositories.IRecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class ImageServiceTest {

    @Mock
    IRecipeRepository recipeRepository;

    IImageService imageService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        imageService =new ImageService(recipeRepository);
    }

    @Test
    public void saveImageFile() throws Exception {
        //given
        Long id = new Long(1);
        MultipartFile multipartFile = new MockMultipartFile("imagefile","test.text","text/plain","hello world!!".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile(id,multipartFile);

        //then
        verify(recipeRepository,times(1)).save(recipeArgumentCaptor.capture());
        Recipe savedRecipe = recipeArgumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length,savedRecipe.getImage().length);
    }

}