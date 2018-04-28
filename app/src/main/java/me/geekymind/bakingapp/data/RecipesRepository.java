package me.geekymind.bakingapp.data;

import io.reactivex.Single;
import java.util.List;
import me.geekymind.bakingapp.data.entity.Recipe;
import me.geekymind.bakingapp.data.remote.RecipesRemote;
import me.geekymind.bakingapp.data.remote.RemotesFactory;

/**
 * Created by Mohamed Ibrahim on 4/14/18.
 */
public class RecipesRepository {

  private final RecipesRemote recipesRemote;

  public RecipesRepository() {
    recipesRemote = RemotesFactory.newRecipesRemote();
  }

  public Single<List<Recipe>> getRecipes() {
    return recipesRemote.getRecipes();
  }
}
