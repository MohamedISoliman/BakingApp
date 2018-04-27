package me.geekymind.bakingapp.ui.reciepedetails.ingrediants;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.base.AppBaseAdapter;
import me.geekymind.bakingapp.data.entity.Ingredient;
import me.geekymind.bakingapp.databinding.ItemIngredientBinding;

/**
 * Created by Mohamed Ibrahim on 4/27/18.
 */
public class IngredientsAdapter
    extends AppBaseAdapter<IngredientsAdapter.IngredientViewHolder, Ingredient> {

  @NonNull
  @Override
  public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ItemIngredientBinding itemIngredientBinding =
        DataBindingUtil.inflate(inflater, R.layout.item_ingredient, parent, false);
    return new IngredientViewHolder(itemIngredientBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
    Ingredient ingredient = getDataItem(position);
    holder.bindData(ingredient);
  }

  public class IngredientViewHolder extends RecyclerView.ViewHolder {

    private final ItemIngredientBinding itemIngredientBinding;

    public IngredientViewHolder(ItemIngredientBinding itemIngredientBinding) {
      super(itemIngredientBinding.getRoot());
      this.itemIngredientBinding = itemIngredientBinding;
    }

    public void bindData(Ingredient ingredient) {
      itemIngredientBinding.ingredientTitle.setText(ingredient.getIngredient());
      itemIngredientBinding.ingredientMeasure.setText(
          String.format("%s:%s ", ingredient.getQuantity(), ingredient.getMeasure()));
    }
  }
}
