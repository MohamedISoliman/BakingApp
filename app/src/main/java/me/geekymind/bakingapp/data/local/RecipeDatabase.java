package me.geekymind.bakingapp.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import me.geekymind.bakingapp.data.entity.Ingredient;
import me.geekymind.bakingapp.data.entity.Recipe;
import timber.log.Timber;

/**
 * Created by Mohamed Ibrahim on 5/19/18.
 */
@Database(entities = { Recipe.class, Ingredient.class }, version = 9, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

  public abstract RecipesLocal recipesDao();

  public Completable insertIngredients(Ingredient... ingredients) {
    return Completable.fromAction(() -> recipesDao().insertIngredients(ingredients))
        .subscribeOn(Schedulers.io());
  }

  public Completable insertRecipes(Recipe... recipes) {
    return Completable.fromAction(() -> recipesDao().insertRecipes(recipes))
        .subscribeOn(Schedulers.io());
  }

  public Observable<List<Ingredient>> getIngredients(double recipeId) {
    return recipesDao().getIngredients(recipeId)
        .toObservable();
  }

  public Observable<List<Recipe>> getRecipes() {
    return recipesDao().getRecipes()
        .doOnError(Timber::e)
        .toObservable()
        .flatMap(Observable::fromIterable)
        .flatMap(this::attachIngredientsToRecipe)
        .toList()
        .toObservable();
  }

  private Observable<Recipe> attachIngredientsToRecipe(Recipe recipe) {
    return Observable.just(recipe)
        .zipWith(getIngredients(recipe.getId()), (recipe1, ingredients) -> {
          recipe1.setIngredients(ingredients);
          return recipe1;
        });
  }

  public Single<Recipe> getSelectedRecipe(long selectedRecipeId) {
    return recipesDao().getSelectedRecipe(selectedRecipeId)
        .toObservable()
        .flatMap(this::attachIngredientsToRecipe)
        .singleOrError();
  }
}
