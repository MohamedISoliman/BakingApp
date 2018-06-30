package me.geekymind.bakingapp.data;

import android.support.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import me.geekymind.bakingapp.data.entity.Ingredient;
import me.geekymind.bakingapp.data.entity.Recipe;
import me.geekymind.bakingapp.data.local.PreferenceHelper;
import me.geekymind.bakingapp.data.local.RecipeDatabase;
import me.geekymind.bakingapp.data.remote.RecipesRemote;
import me.geekymind.bakingapp.data.remote.RemotesFactory;

/**
 * Created by Mohamed Ibrahim on 4/14/18.
 */
public class RecipesRepository {

  private final RecipesRemote recipesRemote;
  private RecipeDatabase database;
  private final PreferenceHelper preferenceHelper;

  public RecipesRepository(RecipeDatabase database, PreferenceHelper preferenceHelper) {
    this.database = database;
    this.preferenceHelper = preferenceHelper;
    recipesRemote = RemotesFactory.newRecipesRemote();
  }

  public Single<List<Recipe>> getRecipes() {
    return recipesRemote.getRecipes()
        .toObservable()
        .flatMap(Observable::fromIterable)
        .flatMap(recipe -> Observable.just(recipe)
            .zipWith(insertIngredients(recipe), (recipeItem, ignored) -> recipeItem))
        .toList()
        .flatMap(recipes -> database.insertRecipes(recipes.toArray(new Recipe[0]))
            .andThen(Single.just(recipes)));
  }

  public Single<List<Recipe>> getLocalRecipes() {
    return database.getRecipes()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .singleOrError();
  }

  public Single<List<Ingredient>> getIngredientsForSelectedRecipe(){
      return getSelectedRecipe().map(recipe -> recipe.getIngredients());
  }

  public Single<Recipe> getSelectedRecipe() {
    long selectedRecipeId = preferenceHelper.getSelectedRecipeId();
    return database.getSelectedRecipe(selectedRecipeId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }


  private Observable<List<Ingredient>> insertIngredients(Recipe recipe) {
    return Observable.fromIterable(recipe.getIngredients())
        .map(ingredient -> {
          ingredient.setRecipeId(recipe.getId());
          return ingredient;
        })
        .toList()
        .toObservable()
        .flatMap(ingredients -> database.insertIngredients(toArray(ingredients))
            .andThen(Observable.just(ingredients)));
  }

  @NonNull
  private Ingredient[] toArray(List<Ingredient> ingredients) {
    return ingredients.toArray(new Ingredient[0]);
  }

  public void saveSelectedRecipeId(long id) {
    preferenceHelper.saveSelectedRecipeId(id);
  }
}
