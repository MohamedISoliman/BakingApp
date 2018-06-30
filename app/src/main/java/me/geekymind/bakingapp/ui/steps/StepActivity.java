package me.geekymind.bakingapp.ui.steps;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.entity.Step;
import me.geekymind.bakingapp.databinding.ActivityStepBinding;

/**
 * Created by Mohamed Ibrahim on 4/28/18.
 */
public class StepActivity extends AppCompatActivity {

    private static final String KEY_STEP = "INGREDIENT";
    private static final String KEY_STEPS_LIST = "STEPS_LIST";
    private ActivityStepBinding binding;
    private Step step;
    private List<Step> stepList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_step);

        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable(KEY_STEP);
            stepList = savedInstanceState.getParcelableArrayList(KEY_STEPS_LIST);
        } else {
            if (getIntent() != null && getIntent().getExtras() != null) {
                step = getIntent().getParcelableExtra(KEY_STEP);
                stepList = getIntent().getParcelableArrayListExtra(KEY_STEPS_LIST);
            }
        }

        initPagerAdapter();

        setupToolbar();
    }

    private void initPagerAdapter() {
        StepsPagerAdapter pagerAdapter = new StepsPagerAdapter(getSupportFragmentManager(), stepList);
        binding.stepsViewpager.setAdapter(pagerAdapter);
        binding.stepsViewpager.setCurrentItem(stepList.indexOf(step));
        binding.stepsViewpager.setOffscreenPageLimit(1);
        binding.tabLayout.setupWithViewPager(binding.stepsViewpager);
    }

    public static void start(Context context, Step step, List<Step> steps) {
        Intent starter = new Intent(context, StepActivity.class);
        starter.putExtra(KEY_STEP, step);
        starter.putParcelableArrayListExtra(KEY_STEPS_LIST, (ArrayList<? extends Parcelable>) steps);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (step != null && stepList != null) {
            outState.putParcelable(KEY_STEP, step);
            outState.putParcelableArrayList(KEY_STEPS_LIST, (ArrayList<? extends Parcelable>) stepList);
        }
        super.onSaveInstanceState(outState);
    }
}
