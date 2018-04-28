package me.geekymind.bakingapp.ui.stepdetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.entity.Step;

/**
 * Created by Mohamed Ibrahim on 4/28/18.
 */
public class StepFragment extends Fragment {

  private static final String KEY_STEP = "STEP";

  public static StepFragment newInstance(Step step) {
    Bundle args = new Bundle();
    StepFragment fragment = new StepFragment();
    args.putParcelable(KEY_STEP, step);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragement_step, container, false);
    return super.onCreateView(inflater, container, savedInstanceState);
  }
}
