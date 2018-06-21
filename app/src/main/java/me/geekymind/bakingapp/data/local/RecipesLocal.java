package me.geekymind.bakingapp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
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

  @Query("SELECT * FROM recipes where id like:id")
  Single<List<Recipe>> getRecipes(double id);

  @Insert
  void insertIngredients(Ingredient... ingredients);

  @Insert
  void insertRecipes(Recipe... recipes);
}
