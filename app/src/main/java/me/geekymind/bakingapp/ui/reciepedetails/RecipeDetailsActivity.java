package me.geekymind.bakingapp.ui.reciepedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.entity.Ingredient;
import me.geekymind.bakingapp.data.entity.Recipe;

/**
 * Created by Mohamed Ibrahim on 4/27/18.
 */
public class RecipeDetailsActivity extends AppCompatActivity {

  private static final String KEY_INGREDIENT = "INGREDIENT";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_details);

    if (getIntent() != null && getIntent().getExtras() != null) {
      Recipe recipe = getIntent().getParcelableExtra(KEY_INGREDIENT);
      RecipeDetailsFragment fragment = RecipeDetailsFragment.newInstance(recipe);
      FragmentManager supportFragmentManager = getSupportFragmentManager();
      FragmentTransaction transaction = supportFragmentManager.beginTransaction();
      transaction.add(R.id.fragment_container, fragment).commit();
    }
  }

  public static void start(Context context, Recipe recipe) {
    Intent starter = new Intent(context, RecipeDetailsActivity.class);
    starter.putExtra(KEY_INGREDIENT, recipe);
    context.startActivity(starter);
  }
}
