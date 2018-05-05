package me.geekymind.bakingapp.ui.steps;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.Collections;
import java.util.List;
import me.geekymind.bakingapp.data.entity.Step;

/**
 * Created by Mohamed Ibrahim on 5/5/18.
 */
public class StepsPagerAdapter extends FragmentPagerAdapter {

  List<Step> steps = Collections.emptyList();

  public StepsPagerAdapter(FragmentManager fm, List<Step> steps) {
    super(fm);
    this.steps = steps;
  }

  @Override
  public Fragment getItem(int position) {
    return StepFragment.newInstance(steps.get(position));
  }

  @Override
  public int getCount() {
    return steps.size();
  }

  @Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    return "Step:" + position;
  }
}
