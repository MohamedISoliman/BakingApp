package me.geekymind.bakingapp.ui.reciepedetails.stepdetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.entity.Step;
import me.geekymind.bakingapp.databinding.ActivityStepBinding;

/**
 * Created by Mohamed Ibrahim on 4/28/18.
 */
public class StepActivity extends AppCompatActivity {

  private static final String KEY_STEP = "INGREDIENT";
  private ActivityStepBinding binding;
  private Step step;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_step);
    if (getIntent() != null && getIntent().getExtras() != null) {
      step = getIntent().getParcelableExtra(KEY_STEP);
      StepFragment fragment = StepFragment.newInstance(step);
      FragmentManager supportFragmentManager = getSupportFragmentManager();
      FragmentTransaction transaction = supportFragmentManager.beginTransaction();
      transaction.add(R.id.fragment_container, fragment).commit();
    }

    setupToolbar();
  }

  public static void start(Context context, Step step) {
    Intent starter = new Intent(context, StepActivity.class);
    starter.putExtra(KEY_STEP, step);
    context.startActivity(starter);
  }

  private void setupToolbar() {
    android.support.v7.widget.Toolbar toolbar = binding.getRoot().findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle(step.getShortDescription());
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
