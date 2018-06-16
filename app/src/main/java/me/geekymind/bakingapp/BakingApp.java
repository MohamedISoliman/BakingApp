package me.geekymind.bakingapp;

import android.app.Application;
import com.squareup.picasso.Picasso;
import me.geekymind.bakingapp.di.AppDependencies;
import timber.log.Timber;

/**
 * Created by Mohamed Ibrahim on 4/14/18.
 */
public class BakingApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    Timber.plant(new Timber.DebugTree());
    Picasso.get().setLoggingEnabled(true);
    AppDependencies.createGraph(this);
  }
}
