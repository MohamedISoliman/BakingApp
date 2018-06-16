package me.geekymind.bakingapp.ui.recipes;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.databinding.ActivityRecipesBinding;

/**
 * Created by Mohamed Ibrahim on 4/14/18.
 */
public class RecipesActivity extends AppCompatActivity {

  private ActivityRecipesBinding recipesBinding;
  private RecipesViewModel viewModel;
  private RecipesAdapter recipesAdapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    recipesBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipes);
    viewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
    setupViews();
    setupListeners();
    viewModel.loadRecipes();
  }

  private void setupViews() {
    recipesAdapter = new RecipesAdapter();
    RecyclerView recyclerView = recipesBinding.recipesRecyclerView;
    boolean useGrid = getResources().getBoolean(R.bool.tablet_mode);
    RecyclerView.LayoutManager layoutManager =
        useGrid ? new GridLayoutManager(this, 3) : new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(recipesAdapter);
    ViewCompat.setNestedScrollingEnabled(recyclerView, false);
    recipesBinding.refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
    recipesBinding.refreshLayout.setOnRefreshListener(() -> {
      //TODO: what is the best way to load more .. search!
    });
  }

  private void setupListeners() {
    viewModel.recipesObservable().subscribe(recipesAdapter::setData);
    viewModel.loadingObservable().subscribe(refreshing -> {
      recipesBinding.refreshLayout.setEnabled(refreshing);
      recipesBinding.refreshLayout.setRefreshing(refreshing);
    });
    viewModel.errorObservable().subscribe(error -> Toasty.error(this, error));
  }
}
