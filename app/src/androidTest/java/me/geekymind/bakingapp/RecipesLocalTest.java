package me.geekymind.bakingapp;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import java.util.List;
import me.geekymind.bakingapp.data.entity.Ingredient;
import me.geekymind.bakingapp.data.local.RecipeDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Mohamed Ibrahim on 6/13/18.
 */
public class RecipesLocalTest {

  RecipeDatabase recipeDatabase;

  @Before
  public void init() {
    recipeDatabase =
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), RecipeDatabase.class)
            .build();
  }

  @After
  public void closeDb() {
    recipeDatabase.close();
  }

  @Test
  public void insertIngredient() {
    //Ingredient ingredient = TestDataFactory.createRandomIngredient();
    //double recipeId = 13.5;
    //ingredient.setRecipeId(recipeId);
    //recipeDatabase.insertIngredients(ingredient).blockingAwait();
    //List<Ingredient> ingredients = recipeDatabase.getIngredients(recipeId).blockingSingle();
    //Ingredient dbItem = ingredients.get(0);
    //assertNotNull(dbItem);
    //assertEquals(dbItem.getRecipeId(), recipeId, 1);
  }
}
