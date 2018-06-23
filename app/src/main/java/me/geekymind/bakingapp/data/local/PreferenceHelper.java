package me.geekymind.bakingapp.data.local;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mohamed Ibrahim on 6/23/18.
 */
public class PreferenceHelper {

  private static final String PREF_FILE_NAME = "BackingAppPref";
  private final Context context;
  private static final String KEY_RECIPE_ID = "RECIPE_ID";
  private final SharedPreferences pref;

  public PreferenceHelper(Context context) {
    this.context = context;
    pref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
  }

  @SuppressLint("ApplySharedPref")
  public void saveSelectedRecipeId(long recipeId) {
    pref.edit().putLong(KEY_RECIPE_ID, recipeId).commit();
  }

  public long getSelectedRecipeId() {
    return pref.getLong(KEY_RECIPE_ID, -1);
  }
}

