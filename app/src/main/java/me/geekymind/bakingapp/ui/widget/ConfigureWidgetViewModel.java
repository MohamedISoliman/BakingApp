package me.geekymind.bakingapp.ui.widget;

import android.arch.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import java.util.List;
import me.geekymind.bakingapp.data.RecipesRepository;
import me.geekymind.bakingapp.data.entity.Recipe;
import me.geekymind.bakingapp.di.AppDependencies;

/**
 * Created by Mohamed Ibrahim on 6/23/18.
 */
class ConfigureWidgetViewModel extends ViewModel {

  private final RecipesRepository recipesRepository;
  private BehaviorSubject<List<Recipe>> recipeSubject = BehaviorSubject.create();
  private Disposable disposable;

  public ConfigureWidgetViewModel() {
    recipesRepository = AppDependencies.getInstance().getRecipesRepository();
  }

  void loadLocalRecipes() {
    disposable = recipesRepository.getLocalRecipes().subscribe(recipes -> {
      recipeSubject.onNext(recipes);
    }, throwable -> {
      recipeSubject.onError(throwable);
    });
  }

  public Observable<List<Recipe>> getRecipeSubject() {
    return recipeSubject;
  }

  public void saveSelectedRecipeId(long id) {
    recipesRepository.saveSelectedRecipeId(id);
  }

  @Override
  protected void onCleared() {
    disposable.dispose();
    recipeSubject.onComplete();
    super.onCleared();
  }
}
