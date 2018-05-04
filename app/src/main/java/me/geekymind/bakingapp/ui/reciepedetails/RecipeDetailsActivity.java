package me.geekymind.bakingapp.ui.reciepedetails;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.entity.Recipe;
import me.geekymind.bakingapp.data.entity.Step;
import me.geekymind.bakingapp.databinding.ActivityRecipeDetailsBinding;
import me.geekymind.bakingapp.ui.reciepedetails.stepdetail.StepFragment;

/**
 * Created by Mohamed Ibrahim on 4/27/18.
 */
public class RecipeDetailsActivity extends AppCompatActivity {

  private static final String KEY_INGREDIENT = "INGREDIENT";
  private ActivityRecipeDetailsBinding binding;
  private Recipe recipe;
  private boolean isTabletMode;
  private RecipeDetailsViewModel viewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);
    recipe = getIntent().getParcelableExtra(KEY_INGREDIENT);
    viewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);
    viewModel.setParam(recipe);
    isTabletMode = getResources().getBoolean(R.bool.tablet_mode);

    addRecipeFragment();
    if (isTabletMode) {
      replaceStepFragment(viewModel.getSelectedStep());
      viewModel.selectedStepObservable().subscribe(this::replaceStepFragment);
    }
    setupToolbar();
  }

  private void replaceStepFragment(Step step) {
    StepFragment fragment = StepFragment.newInstance(step);
    FragmentManager supportFragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = supportFragmentManager.beginTransaction();
    transaction.replace(R.id.instructions_fragment, fragment).commitAllowingStateLoss();
  }

  private void addRecipeFragment() {
    RecipeDetailsFragment fragment = RecipeDetailsFragment.newInstance(recipe);
    FragmentManager supportFragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = supportFragmentManager.beginTransaction();
    transaction.add(R.id.fragment_container, fragment).commit();
  }

  private void setupToolbar() {
    android.support.v7.widget.Toolbar toolbar = binding.getRoot().findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle(recipe.getName());
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  public static void start(Context context, Recipe recipe) {
    Intent starter = new Intent(context, RecipeDetailsActivity.class);
    starter.putExtra(KEY_INGREDIENT, recipe);
    context.startActivity(starter);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
