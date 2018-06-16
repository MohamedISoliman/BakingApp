package me.geekymind.bakingapp;

import com.google.gson.Gson;
import me.geekymind.bakingapp.data.entity.Ingredient;

/**
 * Created by Mohamed Ibrahim on 6/13/18.
 */
class TestDataFactory {
  public static Ingredient createRandomIngredient() {
    String json = " {\n"
        + "        \"quantity\": 2,\n"
        + "        \"measure\": \"CUP\",\n"
        + "        \"ingredient\": \"Graham Cracker crumbs\"\n"
        + "      }";
    Gson gson = new Gson();
    Ingredient ingredient = gson.fromJson(json, Ingredient.class);
    return ingredient;
  }
}
