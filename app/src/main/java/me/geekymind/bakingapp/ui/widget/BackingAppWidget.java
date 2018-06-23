package me.geekymind.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;
import java.util.List;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.RecipesRepository;
import me.geekymind.bakingapp.data.entity.Ingredient;
import me.geekymind.bakingapp.di.AppDependencies;

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

      List<Ingredient> ingredients = recipe.getIngredients();
      for (Ingredient item : ingredients) {
        RemoteViews ingredientView =
            new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_list_item);
        String line =
            item.getIngredientDescription() + "- " + String.format("%s:%s ", item.getQuantity(),
                item.getMeasure());

        ingredientView.setTextViewText(R.id.widget_ingredient_name, line);
        views.addView(R.id.ingredient_list, ingredientView);
      }
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

