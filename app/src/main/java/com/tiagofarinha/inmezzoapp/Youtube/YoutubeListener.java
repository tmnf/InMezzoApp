package com.tiagofarinha.inmezzoapp.Youtube;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

public class YoutubeListener implements YouTubePlayer.OnInitializedListener {

    private String url;

    public YoutubeListener(String url) {
        this.url = url;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        try {
            youTubePlayer.loadVideo(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
