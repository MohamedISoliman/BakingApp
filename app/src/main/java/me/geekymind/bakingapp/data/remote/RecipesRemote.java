package me.geekymind.bakingapp.data.remote;

import io.reactivex.Single;
import java.util.List;
import me.geekymind.bakingapp.data.entity.Recipe;
import retrofit2.http.GET;

/**
 * Created by Mohamed Ibrahim on 4/13/18.
 */
public interface RecipesRemote {
  static final String END_POINT =
      "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

  @GET(END_POINT)
  Single<List<Recipe>> getRecipes();
}
