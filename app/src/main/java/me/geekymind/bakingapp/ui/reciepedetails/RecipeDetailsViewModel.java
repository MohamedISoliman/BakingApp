package me.geekymind.bakingapp.ui.reciepedetails;

import android.arch.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.geekymind.bakingapp.data.RecipesRepository;
import me.geekymind.bakingapp.data.entity.Recipe;
import me.geekymind.bakingapp.data.entity.Step;
import me.geekymind.bakingapp.di.AppDependencies;

/**
 * Created by Mohamed Ibrahim on 4/27/18.
 */
public class RecipeDetailsViewModel extends ViewModel {

  private Recipe param;
  private Step selectedStep;
  private PublishSubject<Step> selectedStepSubject = PublishSubject.create();
  private final RecipesRepository repository;

  public RecipeDetailsViewModel() {
    repository = AppDependencies.getInstance().getRecipesRepository();
  }

  public void setParam(Recipe param) {
    this.param = param;
  }

  public Step getSelectedStep() {
    if (selectedStep != null) {
      return selectedStep;
    }
    selectedStep = param.getSteps().get(0);
    return selectedStep;
  }

  public Recipe getRecipe() {
    return param;
  }

  public void setSelectedStep(Step step) {
    selectedStep = step;
    selectedStepSubject.onNext(selectedStep);
  }

  public Observable<Step> selectedStepObservable() {
    return selectedStepSubject;
  }
}
