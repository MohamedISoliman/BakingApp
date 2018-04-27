package me.geekymind.bakingapp.ui.reciepedetails;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.entity.Recipe;
import me.geekymind.bakingapp.databinding.FragmentRecipeDetailsBinding;
import me.geekymind.bakingapp.ui.reciepedetails.ingrediants.IngredientsAdapter;

/**
 * Created by Mohamed Ibrahim on 4/27/18.
 */
public class RecipeDetailsFragment extends Fragment {

  private FragmentRecipeDetailsBinding binding;
  private static final String KEY_INGREDIENT = "IGREDIENT";
  private IngredientsAdapter adapterIngredients;
  private Recipe recipe;

  public static RecipeDetailsFragment newInstance(Recipe recipe) {
    Bundle args = new Bundle();
    RecipeDetailsFragment fragment = new RecipeDetailsFragment();
    args.putParcelable(KEY_INGREDIENT, recipe);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding =
        DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_recipe_details, container,
            false);
    recipe = getArguments().getParcelable(KEY_INGREDIENT);
    setupToolbar();
    setupRecycler();
    return binding.getRoot();
  }

  private void setupToolbar() {
    setHasOptionsMenu(true);
    Toolbar toolbar = binding.getRoot().findViewById(R.id.toolbar);
    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    supportActionBar.setTitle(recipe.getName());
    supportActionBar.setDisplayHomeAsUpEnabled(true);
  }

  private void setupRecycler() {
    adapterIngredients = new IngredientsAdapter();
    RecyclerView recyclerView = binding.recyclerIngredients;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapterIngredients);
    adapterIngredients.setData(recipe.getIngredients());
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case android.R.id.home:
        getActivity().onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
