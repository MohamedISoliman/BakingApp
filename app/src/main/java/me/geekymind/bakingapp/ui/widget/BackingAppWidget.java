package me.geekymind.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.RecipesRepository;
import me.geekymind.bakingapp.data.entity.Ingredient;
import me.geekymind.bakingapp.di.AppDependencies;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link BackingAppWidgetConfigureActivity
 * BackingAppWidgetConfigureActivity}
 */
public class BackingAppWidget extends AppWidgetProvider {

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId);
    }
  }

  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
    RecipesRepository recipesRepository = AppDependencies.getInstance().getRecipesRepository();
    recipesRepository.getSelectedRecipe().toObservable().take(1).subscribe(recipe -> {

      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.backing_app_widget);

      views.setTextViewText(R.id.appwidget_text, recipe.getName());

      Intent intent = new Intent(context, ListViewWidgetService.class);
      Bundle bundle = new Bundle();
      bundle.putParcelableArrayList(ListViewWidgetService.KEY_INGREDIENTS_LIST,
              (ArrayList<? extends Parcelable>) recipe.getIngredients());
      intent.putExtra(ListViewWidgetService.KEY_INGREDIENTS_LIST,bundle);
      views.setRemoteAdapter(R.id.ingredient_list, intent);

      appWidgetManager.updateAppWidget(appWidgetId, views);
    }, throwable -> {
      Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_LONG).show();
    });
  }

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) {
    // When the user deletes the widget, delete the preference associated with it.
    for (int appWidgetId : appWidgetIds) {
      BackingAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
    }
  }

  @Override
  public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
  }

  @Override
  public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }
}

