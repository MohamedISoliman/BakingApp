package me.geekymind.bakingapp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import io.reactivex.Single;
import java.util.List;
import me.geekymind.bakingapp.data.entity.Ingredient;

/**
 * Created by Mohamed Ibrahim on 5/19/18.
 */
@Dao
public interface RecipesLocal {

  @Query("SELECT * FROM ingredients where recipeId like:id")
  Single<List<Ingredient>> getIngredients(double id);

  @Insert
  void insertIngredients(Ingredient... ingredients);

}
