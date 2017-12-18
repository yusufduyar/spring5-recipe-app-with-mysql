package com.spring5.recipeapp.bootstrap;

import com.spring5.recipeapp.domain.*;
import com.spring5.recipeapp.repositories.ICategoryRepository;
import com.spring5.recipeapp.repositories.IRecipeRepository;
import com.spring5.recipeapp.repositories.IUnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private ICategoryRepository categoryRepository;
    private IRecipeRepository recipeRepository;
    private IUnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(ICategoryRepository categoryRepository, IRecipeRepository recipeRepository, IUnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {

        Recipe guacamole = new Recipe();
        guacamole.setCookTime(0);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setPrepTime(10);
        guacamole.setDescription("Guacamole");
        guacamole.setServings(4);
        guacamole.setSource("Simply Recipes");
        guacamole.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n");

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n");

        guacamole.setNotes(guacamoleNotes);
        Set<Category> guacamoleCategories = guacamole.getCategories();
        guacamoleCategories.add(categoryRepository.findByDescription("American").get());
        guacamoleCategories.add(categoryRepository.findByDescription("Mexican").get());

        Optional<UnitOfMeasure> eachMeasure = unitOfMeasureRepository.findByDescription("Each");
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        Optional<UnitOfMeasure> cup = unitOfMeasureRepository.findByDescription("Cup");
        Optional<UnitOfMeasure> pinch = unitOfMeasureRepository.findByDescription("Pinch");
        Optional<UnitOfMeasure> ounce = unitOfMeasureRepository.findByDescription("Ounce");
        Optional<UnitOfMeasure> dash = unitOfMeasureRepository.findByDescription("Dash");
        Optional<UnitOfMeasure> pint = unitOfMeasureRepository.findByDescription("Pint");

        guacamole.addIngredient(new Ingredient("ripe avocados",new BigDecimal("2"),eachMeasure.get()));
        guacamole.addIngredient(new Ingredient("Kosher salt",new BigDecimal(".5"),teaspoon.get()));
        guacamole.addIngredient(new Ingredient("fresh lime juice or lemon juice",new BigDecimal("1"),tablespoon.get()));
        guacamole.addIngredient(new Ingredient("minced red onion or thinly sliced green onion",new BigDecimal("2"),tablespoon.get()));
        guacamole.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced",new BigDecimal("2"),eachMeasure.get()));
        guacamole.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tablespoon.get()));
        guacamole.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dash.get()));
        guacamole.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachMeasure.get()));

        recipeRepository.save(guacamole);

    }
}
