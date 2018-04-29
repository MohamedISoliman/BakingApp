package me.geekymind.bakingapp.ui.recipes;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.entity.Recipe;
import me.geekymind.bakingapp.databinding.ItemRecipeBinding;
import me.geekymind.bakingapp.ui.RecyclerAdapter;
import me.geekymind.bakingapp.ui.reciepedetails.RecipeDetailsActivity;

/**
 * Created by Mohamed Ibrahim on 4/14/18.
 */
public class RecipesAdapter extends RecyclerAdapter<Recipe, RecipesAdapter.RecipeViewHolder> {

  @NonNull
  @Override
  public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ItemRecipeBinding itemRecipeBinding =
        DataBindingUtil.inflate(inflater, R.layout.item_recipe, parent, false);
    return new RecipeViewHolder(itemRecipeBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
    Recipe recipe = getData().get(position);
    holder.bindData(recipe);
  }

  class RecipeViewHolder extends RecyclerView.ViewHolder {

    private ItemRecipeBinding recipeBinding;
    private final Context context;

    public RecipeViewHolder(ItemRecipeBinding recipeBinding) {
      super(recipeBinding.getRoot());
      this.recipeBinding = recipeBinding;
      context = recipeBinding.getRoot().getContext();
    }

    public void bindData(Recipe recipe) {
      if (!recipe.getImage().isEmpty()) {
        Picasso.get().load(recipe.getImage()).into(recipeBinding.recipeImage);
      }
      recipeBinding.recipeServings.setText(String.valueOf(recipe.getServings()));
      recipeBinding.recipeTitle.setText(recipe.getName());
      recipeBinding.itemRecipeContainer.setOnClickListener(v -> navigateToDetails(recipe));
    }

    private void navigateToDetails(Recipe recipe) {
      RecipeDetailsActivity.start(context, recipe);
    }
  }
}
