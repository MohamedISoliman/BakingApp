package me.geekymind.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import es.dmoral.toasty.Toasty;
import java.util.Collections;
import java.util.List;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.entity.Recipe;
import me.geekymind.bakingapp.databinding.BackingAppWidgetConfigureActivityBinding;
import me.geekymind.bakingapp.databinding.ItemWidgetConfigureRecipeBinding;

/**
 * The configuration screen for the {@link BackingAppWidget BackingAppWidget} AppWidget.
 */
public class BackingAppWidgetConfigureActivity extends AppCompatActivity {

  private static final String PREFS_NAME = "me.geekymind.bakingapp.ui.widget.BackingAppWidget";
  private static final String PREF_PREFIX_KEY = "appwidget_";
  int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
  BackingAppWidgetConfigureActivityBinding binding;

  private void updateWidget(Context context) {
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
    BackingAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

    // Make sure we pass back the original appWidgetId
    Intent resultValue = new Intent();
    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
    setResult(RESULT_OK, resultValue);
    finish();
  }

  private ConfigureWidgetViewModel viewModel;
  private WidgetConfigureAdapter adapter;

  public BackingAppWidgetConfigureActivity() {
    super();
  }

  static void deleteTitlePref(Context context, int appWidgetId) {
    SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
    prefs.remove(PREF_PREFIX_KEY + appWidgetId);
    prefs.apply();
  }

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    // Set the result to CANCELED.  This will cause the widget host to cancel
    // out of the widget placement if the user presses the back button.
    setResult(RESULT_CANCELED);
    binding = DataBindingUtil.setContentView(this, R.layout.backing_app_widget_configure_activity);
    viewModel = ViewModelProviders.of(this).get(ConfigureWidgetViewModel.class);
    setupRecycler();
    viewModel.loadLocalRecipes();

    // Find the widget id from the intent.
    Intent intent = getIntent();
    Bundle extras = intent.getExtras();
    if (extras != null) {
      mAppWidgetId =
          extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    // If this activity was started with an intent without an app widget ID, finish with an error.
    if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
      finish();
      return;
    }
  }

  private void setupRecycler() {
    RecyclerView recyclerView = findViewById(R.id.configure_recipe_list);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setHasFixedSize(true);
    adapter = new WidgetConfigureAdapter();
    recyclerView.setAdapter(adapter);
    viewModel.getRecipeSubject().subscribe(recipes -> {
      adapter.setData(recipes);
    }, throwable -> {
      Toasty.error(this, throwable.getMessage()).show();
    });
  }

  private class WidgetConfigureAdapter
      extends RecyclerView.Adapter<WidgetConfigureAdapter.ConfigureViewHolder> {

    private List<Recipe> recipes = Collections.emptyList();

    public void setData(List<Recipe> recipes) {
      this.recipes = recipes;
      notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConfigureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      ItemWidgetConfigureRecipeBinding viewDataBinding =
          DataBindingUtil.inflate(inflater, R.layout.item_widget_configure_recipe, parent, false);

      return new ConfigureViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfigureViewHolder holder, int position) {
      holder.bindData(recipes.get(position));
    }

    @Override
    public int getItemCount() {
      return recipes.size();
    }

    class ConfigureViewHolder extends RecyclerView.ViewHolder {

      private ItemWidgetConfigureRecipeBinding viewDataBinding;
      private final Context context;

      public ConfigureViewHolder(ItemWidgetConfigureRecipeBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.viewDataBinding = viewDataBinding;
        context = viewDataBinding.getRoot().getContext();
      }

      void bindData(Recipe recipe) {
        viewDataBinding.recipeTitle.setText(recipe.getName());
        viewDataBinding.recipeTitle.setOnClickListener(v -> {
          viewModel.saveSelectedRecipeId(recipe.getId());
          updateWidget(context);
        });
      }
    }
  }
}

