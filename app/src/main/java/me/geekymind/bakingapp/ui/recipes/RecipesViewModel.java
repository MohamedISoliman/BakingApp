package me.geekymind.bakingapp.ui.recipes;

import android.arch.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.util.List;
import me.geekymind.bakingapp.data.RecipesRepository;
import me.geekymind.bakingapp.data.entity.Recipe;
import me.geekymind.bakingapp.di.AppDependencies;

/**
 * Created by Mohamed Ibrahim on 4/14/18.
 */
public class RecipesViewModel extends ViewModel {

  private final RecipesRepository repository;
  private BehaviorSubject<List<Recipe>> recipesSubject = BehaviorSubject.create();
  private Subject<Recipe> selectedRecipe = BehaviorSubject.create();
  private Subject<Boolean> isLoading = BehaviorSubject.createDefault(false);
  private Subject<String> errorMessageSubject = PublishSubject.create();
  private final CompositeDisposable disposables = new CompositeDisposable();
  private Disposable disposable;

  public RecipesViewModel() {
    repository = AppDependencies.getInstance().getRecipesRepository();
  }

  public void loadRecipes() {
    if (disposable == null) {
      disposable = Observable.just("start")
          .subscribeOn(Schedulers.io())
          .flatMapSingle(s -> recipesSubject.getValue() == null ? repository.getRecipes()
              .compose(loadingTransformer()) : Single.just(recipesSubject.getValue()))
          .subscribe(recipes -> recipesSubject.onNext(recipes),
              throwable -> errorMessageSubject.onNext(throwable.getMessage()));
      disposables.add(disposable);
    }
  }

  public Observable<List<Recipe>> recipesObservable() {
    return recipesSubject.observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<Recipe> selectedRecipeObservable() {
    return selectedRecipe.observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<String> errorObservable() {
    return errorMessageSubject.observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<Boolean> loadingObservable() {
    return isLoading.observeOn(AndroidSchedulers.mainThread());
  }

  private <T> SingleTransformer<T, T> loadingTransformer() {
    return upstream -> upstream.doOnSubscribe(__ -> isLoading.onNext(true))
        .doAfterTerminate(() -> isLoading.onNext(false));
  }
}
