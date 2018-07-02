package me.geekymind.bakingapp.ui.steps;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
import com.squareup.picasso.Picasso;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.entity.Step;
import me.geekymind.bakingapp.databinding.FragmentStepBinding;

/**
 * Created by Mohamed Ibrahim on 4/28/18.
 */
public class StepFragment extends Fragment {

  private static final String KEY_STEP = "STEP";
  private static final String KEY_PLAYBACK_POSITION = "PLAYBACK_POSITION";
  private static final String KEY_EXO_IS_PLAYING = "PLAYBACK_STATE";
  private FragmentStepBinding binding;
  private Step step;
  private SimpleExoPlayer simpleExoPlayer;
  private long playBackPosition = -1;
  private boolean playWhenReady = false;

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
    onRestoreState(savedInstanceState);

    binding =
        DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_step, container, false);
    step = getArguments().getParcelable(KEY_STEP);
    binding.stepDescription.setText(step.getDescription());
    handleVideoThumbnail();
    return binding.getRoot();
  }

  private void handleVideoThumbnail() {
    if (step.getThumbnailURL().isEmpty()) {
      binding.videoThumbnail.setVisibility(View.GONE);
    } else {
      Picasso.get()
          .load(step.getThumbnailURL())
          .error(R.drawable.ic_image_black_24dp)
          .into(binding.videoThumbnail);
    }
  }

  private void onRestoreState(@Nullable Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      playBackPosition = savedInstanceState.getLong(KEY_PLAYBACK_POSITION);
      playWhenReady = savedInstanceState.getBoolean(KEY_EXO_IS_PLAYING);
    }
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    savePlaybackPosition(outState);
    super.onSaveInstanceState(outState);
  }

  private void savePlaybackPosition(Bundle outState) {
    if (simpleExoPlayer != null) {
      long currentPositionForBundle = simpleExoPlayer.getCurrentPosition();
      boolean playWhenReadyForBundle = simpleExoPlayer.getPlayWhenReady();
      outState.putLong(KEY_PLAYBACK_POSITION, currentPositionForBundle);
      outState.putBoolean(KEY_EXO_IS_PLAYING, playWhenReadyForBundle);
    }
  }

  private void setupVideoPlayer() {
    if (step.getVideoURL() == null || step.getVideoURL().isEmpty()) {
      binding.videoPlayer.setVisibility(View.GONE);
      return;
    }
    initExoPlayer();
  }

  private void initExoPlayer() {
    binding.videoPlayer.setVisibility(View.VISIBLE);
    MediaSource mediaSource = getMediaSource();
    simpleExoPlayer.prepare(mediaSource);
    resumePlayBack();
  }

  @NonNull
  private MediaSource getMediaSource() {
    TrackSelector trackSelector = new DefaultTrackSelector();
    LoadControl loadControl = new DefaultLoadControl();
    simpleExoPlayer =
        ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), trackSelector,
            loadControl);
    binding.videoPlayer.setPlayer(simpleExoPlayer);
    String userAgent = Util.getUserAgent(getContext(), "BackingApp");
    return new ExtractorMediaSource(Uri.parse(step.getVideoURL()),
        new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null,
        null);
  }

  private void resumePlayBack() {
    if (simpleExoPlayer != null && playBackPosition != -1) {
      simpleExoPlayer.seekTo(playBackPosition);
      simpleExoPlayer.setPlayWhenReady(playWhenReady);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    if (Util.SDK_INT > 23) {
      setupVideoPlayer();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (Util.SDK_INT <= 23 || simpleExoPlayer == null) {
      setupVideoPlayer();
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (Util.SDK_INT <= 23) {
      releaseExoPlayer();
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if (Util.SDK_INT > 23) {
      releaseExoPlayer();
    }
  }

  private void releaseExoPlayer() {
    if (simpleExoPlayer != null) {
      simpleExoPlayer.release();
    }
  }
}
