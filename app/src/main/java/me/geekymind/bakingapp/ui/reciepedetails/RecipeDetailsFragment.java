package me.geekymind.bakingapp.ui.reciepedetails;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.entity.Recipe;
import me.geekymind.bakingapp.databinding.FragmentRecipeDetailsBinding;
import me.geekymind.bakingapp.ui.reciepedetails.ingrediants.IngredientsAdapter;
import me.geekymind.bakingapp.ui.reciepedetails.instructions.InstructionsAdapter;
import me.geekymind.bakingapp.ui.reciepedetails.stepdetail.StepActivity;

/**
 * Created by Mohamed Ibrahim on 4/27/18.
 */
public class RecipeDetailsFragment extends Fragment {

  private FragmentRecipeDetailsBinding binding;
  private static final String KEY_INGREDIENT = "IGREDIENT";
  private IngredientsAdapter adapterIngredients;
  private RecipeDetailsViewModel viewModel;
  private boolean isTabletMode;
  private InstructionsAdapter instructionsAdapter;

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
    viewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailsViewModel.class);
    isTabletMode = getResources().getBoolean(R.bool.tablet_mode);

    return binding.getRoot();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setupRecycler();
  }

  private void setupRecycler() {
    //ingredients
    adapterIngredients = new IngredientsAdapter();
    RecyclerView recyclerView = binding.recyclerIngredients;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapterIngredients);
    adapterIngredients.setData(viewModel.getRecipe().getIngredients());

    //instructions
    instructionsAdapter = new InstructionsAdapter(step -> {
      if (isTabletMode) {
        viewModel.setSelectedStep(step);
      } else {
        StepActivity.start(getContext(), step);
      }
    }, viewModel.getSelectedStep());
    RecyclerView instructionsRecycler = binding.recyclerInstructions;
    instructionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    instructionsRecycler.addItemDecoration(
        new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
    instructionsRecycler.setHasFixedSize(true);
    instructionsRecycler.setAdapter(instructionsAdapter);
    instructionsAdapter.setData(viewModel.getRecipe().getSteps());
  }
}
