package me.geekymind.bakingapp.data;

import android.support.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
import me.geekymind.bakingapp.data.entity.Ingredient;
import me.geekymind.bakingapp.data.entity.Recipe;
import me.geekymind.bakingapp.data.local.RecipeDatabase;
import me.geekymind.bakingapp.data.remote.RecipesRemote;
import me.geekymind.bakingapp.data.remote.RemotesFactory;

/**
 * Created by Mohamed Ibrahim on 4/14/18.
 */
public class RecipesRepository {

  private final RecipesRemote recipesRemote;
  private RecipeDatabase database;

  public RecipesRepository(RecipeDatabase database) {
    this.database = database;
    recipesRemote = RemotesFactory.newRecipesRemote();
  }

  public Single<List<Recipe>> getRecipes() {
    return recipesRemote.getRecipes()
        .toObservable()
        .flatMap(Observable::fromIterable)
        .flatMap(recipe -> Observable.zip(Observable.just(recipe), insertIngredients(recipe),
            (recipeItem, ignored) -> recipeItem))
        .toList();
  }

  private Observable<List<Ingredient>> insertIngredients(Recipe recipe) {
    return Observable.fromIterable(recipe.getIngredients())
        .map(ingredient -> {
          ingredient.setRecipeId(recipe.getId());
          return ingredient;
        })
        .toList()
        .toObservable()
        .doOnNext(ingredients -> database.insertIngredients(toArray(ingredients)));
  }

  @NonNull
  private Ingredient[] toArray(List<Ingredient> ingredients) {
    return ingredients.toArray(new Ingredient[0]);
  }
}
