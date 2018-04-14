package me.geekymind.bakingapp.di;

import me.geekymind.bakingapp.data.RecipesRepository;

/**
 * Created by Mohamed Ibrahim on 4/14/18.
 */
public class AppDependencies {

  private static AppDependencies instance;
  private static final RecipesRepository REPOSITORY = new RecipesRepository();

  private AppDependencies() {
  }

  public static AppDependencies getInstance() {
    if (instance == null) {
      instance = new AppDependencies();
    }
    return instance;
  }

  public RecipesRepository getRecipesRepository() {
    return REPOSITORY;
  }
}
