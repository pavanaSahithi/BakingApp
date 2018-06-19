package pavanasahithi.justbake;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;


public class ItemDetailFragment extends Fragment implements ExoPlayer.EventListener {
    public static final String POSITION_VIDEO = "position";
    public static final String PLAY_BACK = "play_back";
    public SimpleExoPlayerView simpleExoPlayer;
    JsonPojo jsonPojo = null;
    StepsPojo stepsPojo = null;
    SimpleExoPlayer player;
    TextView tv;
    ImageView imageView;
    String videoUrl;
    String thumbNailUrl;
    private boolean playWhenReady;
    private long currentVideoPosition;


    public ItemDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String x = "";
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        tv = (TextView) rootView.findViewById(R.id.id_fragment_details_tv);
        imageView = (ImageView) rootView.findViewById(R.id.id_fragment_image);
        Bundle bundle = getArguments();
        jsonPojo = bundle.getParcelable("recipe");
        stepsPojo = bundle.getParcelable("steps");
        Activity activity = this.getActivity();
        if (jsonPojo != null) {
            activity.setTitle(getString(R.string.ingredients));
            for (int i = 0; i < jsonPojo.getIngredientsPojos().size(); ) {
                String ing = jsonPojo.getIngredientsPojos().get(i).getIngredient();
                float qty = jsonPojo.getIngredientsPojos().get(i).getQuantity();
                String measure = jsonPojo.getIngredientsPojos().get(i).getMeasure();
                x = x + (++i) + ".\t" + ing + "\nQuantity:\t" + qty + "\nMeasure:\t" + measure + "\n\n\n";
            }
        } else if (stepsPojo != null) {
            videoUrl = stepsPojo.getVideoUrl();
            thumbNailUrl = stepsPojo.getThumbnailURl();
            activity.setTitle(stepsPojo.getShortDescription());
            if (!videoUrl.contentEquals("")) {
                simpleExoPlayer = (SimpleExoPlayerView) rootView.findViewById(R.id.id_exoplayer);
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                player = ExoPlayerFactory.newSimpleInstance(rootView.getContext(), trackSelector);
                Uri videoURI = Uri.parse(videoUrl);
                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
                simpleExoPlayer.setPlayer(player);
                player.prepare(mediaSource);
                player.setPlayWhenReady(true);
                simpleExoPlayer.setVisibility(View.VISIBLE);
            } else if (!thumbNailUrl.contentEquals("")) {
                if (thumbNailUrl.endsWith("mp4")) {
                    simpleExoPlayer = (SimpleExoPlayerView) rootView.findViewById(R.id.id_exoplayer);
                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                    player = ExoPlayerFactory.newSimpleInstance(rootView.getContext(), trackSelector);
                    Uri ThumbURI = Uri.parse(thumbNailUrl);
                    DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                    MediaSource mediaSource = new ExtractorMediaSource(ThumbURI, dataSourceFactory, extractorsFactory, null, null);
                    simpleExoPlayer.setPlayer(player);
                    player.prepare(mediaSource);
                    player.setPlayWhenReady(true);
                    simpleExoPlayer.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    Picasso.with(getContext()).load(thumbNailUrl).into(imageView);
                }
            }
            x = stepsPojo.getDescription();
        }
        if (savedInstanceState != null) {
            if (player != null) {
                currentVideoPosition = savedInstanceState.getLong(POSITION_VIDEO);
                player.seekTo(currentVideoPosition);
                playWhenReady = savedInstanceState.getBoolean(PLAY_BACK);
                player.setPlayWhenReady(playWhenReady);
            }

        }
        tv.setText(x);
        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (player != null) {
            currentVideoPosition = player.getCurrentPosition();
            playWhenReady = player.getPlayWhenReady();
            outState.putLong(POSITION_VIDEO, currentVideoPosition);
            outState.putBoolean(PLAY_BACK, playWhenReady);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (simpleExoPlayer != null) {
            if (player.getCurrentPosition() != player.getDuration())
                player.release();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

}
