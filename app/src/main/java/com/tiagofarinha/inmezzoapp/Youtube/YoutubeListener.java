package com.tiagofarinha.inmezzoapp.Youtube;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

public class YoutubeListener implements YouTubePlayer.OnInitializedListener {

    private String video_id;

    public YoutubeListener(String url) {
        this.video_id = url;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        try {
            youTubePlayer.loadVideo(video_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
