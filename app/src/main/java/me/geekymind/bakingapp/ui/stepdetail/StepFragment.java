package me.geekymind.bakingapp.ui.stepdetail;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.entity.Step;
import me.geekymind.bakingapp.databinding.FragmentStepBinding;

/**
 * Created by Mohamed Ibrahim on 4/28/18.
 */
public class StepFragment extends Fragment {

  private static final String KEY_STEP = "STEP";
  private FragmentStepBinding binding;
  private Step step;
  private SimpleExoPlayer simpleExoPlayer;

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
    binding =
        DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_step, container, false);
    step = getArguments().getParcelable(KEY_STEP);
    setupToolbar();
    setupVideoPlayer();
    binding.stepDescription.setText(step.getDescription());
    return binding.getRoot();
  }

  private void setupVideoPlayer() {
    if (step.getVideoURL() == null || step.getVideoURL().isEmpty()) return;
    binding.videoPlayer.setVisibility(View.VISIBLE);
    TrackSelector trackSelector = new DefaultTrackSelector();
    LoadControl loadControl = new DefaultLoadControl();
    simpleExoPlayer =
        ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), trackSelector,
            loadControl);
    binding.videoPlayer.setPlayer(simpleExoPlayer);
    String userAgent = Util.getUserAgent(getContext(), "BackingApp");
    MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(step.getVideoURL()),
        new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null,
        null);
    simpleExoPlayer.prepare(mediaSource);
  }

  private void setupToolbar() {
    setHasOptionsMenu(true);
    Toolbar toolbar = binding.getRoot().findViewById(R.id.toolbar);
    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    supportActionBar.setTitle(step.getShortDescription());
    supportActionBar.setDisplayHomeAsUpEnabled(true);
  }

  @Override
  public void onDestroyView() {
    if (simpleExoPlayer != null) {
      simpleExoPlayer.release();
    }
    super.onDestroyView();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        getActivity().onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
