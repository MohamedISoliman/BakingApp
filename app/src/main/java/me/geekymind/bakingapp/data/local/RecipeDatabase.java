package me.geekymind.bakingapp.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import me.geekymind.bakingapp.data.entity.Ingredient;
import me.geekymind.bakingapp.data.entity.Recipe;

/**
 * Created by Mohamed Ibrahim on 5/19/18.
 */
@Database(entities = { Recipe.class, Ingredient.class }, version = 2, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

  public abstract RecipesLocal recipesDao();

  public Completable insertIngredients(Ingredient... ingredients) {
    return Completable.fromAction(() ->
        recipesDao().insertIngredients(ingredients))
        .subscribeOn(Schedulers.io());
  }

  public Observable<List<Ingredient>> getIngredients(double recipeId) {
    return recipesDao().getIngredients(recipeId).toObservable();
  }
}
