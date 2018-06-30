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
    private long playBackPositon = 0;

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
        setupVideoPlayer();

        return binding.getRoot();
    }

    private void onRestoreState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            playBackPositon = savedInstanceState.getLong(KEY_PLAYBACK_POSITION);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        savePlaybackPosition(outState);
        super.onSaveInstanceState(outState);
    }

    private void savePlaybackPosition(Bundle outState) {
        if (simpleExoPlayer != null) {
            long currentPosition = simpleExoPlayer.getCurrentPosition();
            outState.putLong(KEY_PLAYBACK_POSITION, currentPosition);
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
        simpleExoPlayer.setPlayWhenReady(true);
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
        if (simpleExoPlayer != null) {
            simpleExoPlayer.seekTo(playBackPositon);
            simpleExoPlayer.setPlayWhenReady(true);
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
        if ((Util.SDK_INT <= 23 || simpleExoPlayer == null)) {
            setupVideoPlayer();
        }
    }

    private void releaseExoPlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }
}
