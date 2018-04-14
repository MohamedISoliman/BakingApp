package me.geekymind.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.geekymind.bakingapp.data.remote.RecipesRemote;
import me.geekymind.bakingapp.data.remote.RemotesFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    RecipesRemote remote = RemotesFactory.newRecipesRemote();

    remote.getRecipes()
        .toObservable()
        .flatMap(Observable::fromIterable)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(recipe -> Timber.d(recipe.getName()), Timber::e);
  }
}
