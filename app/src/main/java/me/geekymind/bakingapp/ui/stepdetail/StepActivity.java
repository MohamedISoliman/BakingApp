package me.geekymind.bakingapp.ui.stepdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.entity.Step;

/**
 * Created by Mohamed Ibrahim on 4/28/18.
 */
public class StepActivity extends AppCompatActivity {

  private static final String KEY_STEP = "INGREDIENT";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_details);

    if (getIntent() != null && getIntent().getExtras() != null) {
      Step step = getIntent().getParcelableExtra(KEY_STEP);
      StepFragment fragment = StepFragment.newInstance(step);
      FragmentManager supportFragmentManager = getSupportFragmentManager();
      FragmentTransaction transaction = supportFragmentManager.beginTransaction();
      transaction.add(R.id.fragment_container, fragment).commit();
    }
  }

  public static void start(Context context, Step step) {
    Intent starter = new Intent(context, StepActivity.class);
    starter.putExtra(KEY_STEP, step);
    context.startActivity(starter);
  }
}
