package me.geekymind.bakingapp.ui.recipes;

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

    public RecipeViewHolder(ItemRecipeBinding recipeBinding) {
      super(recipeBinding.getRoot());
      this.recipeBinding = recipeBinding;
    }

    public void bindData(Recipe recipe) {
      Picasso.get()
          .load(recipe.getImage())
          .error(R.drawable.food_place_holder)
          .into(recipeBinding.recipeImage);
      
      recipeBinding.recipeTitle.setText(recipe.getName());
    }
  }
}
