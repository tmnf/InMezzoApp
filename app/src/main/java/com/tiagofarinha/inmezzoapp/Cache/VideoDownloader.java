package com.tiagofarinha.inmezzoapp.Cache;

import com.squareup.picasso.Picasso;
import com.tiagofarinha.inmezzoapp.Models.YoutubeVideo;

import java.io.IOException;

public class VideoDownloader extends Thread {

    /* Handles Video Info Download */

    private ResourceLoader rl;
    private String url, id;

    public VideoDownloader(ResourceLoader rl, String url, String id) {
        this.rl = rl;
        this.url = url;
        this.id = id;
    }

    public void run() {
        getVideoInfo();
    }

    public void getVideoInfo() {

        String download_url = "https://img.youtube.com/vi/" + id + "/maxresdefault.jpg";

        try {
            rl.addToVideoList(new YoutubeVideo(url, Picasso.get().load(download_url).get()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
