package me.geekymind.bakingapp.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import me.geekymind.bakingapp.BakingApp;
import me.geekymind.bakingapp.data.RecipesRepository;
import me.geekymind.bakingapp.data.local.PreferenceHelper;
import me.geekymind.bakingapp.data.local.RecipeDatabase;

/**
 * Created by Mohamed Ibrahim on 4/14/18.
 */
public class AppDependencies {

  private static AppDependencies instance;
  private final RecipesRepository repository;

  private AppDependencies(Context context) {
    RecipeDatabase database = Room.databaseBuilder(context, RecipeDatabase.class, "BackingApp.db")
        .fallbackToDestructiveMigration()
        .build();
    PreferenceHelper preferenceHelper = new PreferenceHelper(context);
    repository = new RecipesRepository(database, preferenceHelper);
  }

  public static void createGraph(Context context) {
    instance = new AppDependencies(context);
  }

  public static AppDependencies getInstance() {
    if (instance == null) {
      throw new IllegalStateException(
          "Init dependencies by Calling createGraph() " + BakingApp.class.getSimpleName());
    }
    return instance;
  }

  public RecipesRepository getRecipesRepository() {
    return repository;
  }
}
