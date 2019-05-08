package com.tiagofarinha.inmezzoapp.Models;

import android.graphics.Bitmap;

public class YoutubeVideo extends YoutubeContainer {

    private Bitmap thumbnail;

    public YoutubeVideo(String url, Bitmap thumbnail) {
        super(url);

        this.thumbnail = thumbnail;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

}
