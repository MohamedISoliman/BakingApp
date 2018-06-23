package me.geekymind.bakingapp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import io.reactivex.Single;
import java.util.List;
import me.geekymind.bakingapp.data.entity.Ingredient;
import me.geekymind.bakingapp.data.entity.Recipe;

/**
 * Created by Mohamed Ibrahim on 5/19/18.
 */
@Dao
public interface RecipesLocal {

  @Query("SELECT * FROM ingredients where recipeId like:id")
  Single<List<Ingredient>> getIngredients(double id);

  @Query("SELECT * FROM recipes")
  Single<List<Recipe>> getRecipes();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertIngredients(Ingredient... ingredients);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertRecipes(Recipe... recipes);

  @Query("SELECT * FROM recipes where id like:selectedRecipeId")
  Single<Recipe> getSelectedRecipe(long selectedRecipeId);
}
