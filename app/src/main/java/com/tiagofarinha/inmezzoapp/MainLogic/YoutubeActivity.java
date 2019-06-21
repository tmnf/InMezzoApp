package com.tiagofarinha.inmezzoapp.MainLogic;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Youtube.YoutubeConfig;
import com.tiagofarinha.inmezzoapp.Youtube.YoutubeListener;

public class YoutubeActivity extends YouTubeBaseActivity {

    private YouTubePlayerView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        video = findViewById(R.id.youtube_viewer);
        String url = getIntent().getStringExtra("url");
        video.initialize(YoutubeConfig.getApiKey(), new YoutubeListener(url));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
