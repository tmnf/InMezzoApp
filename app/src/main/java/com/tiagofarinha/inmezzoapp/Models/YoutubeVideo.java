package com.tiagofarinha.inmezzoapp.Models;

import android.net.Uri;

public class YoutubeVideo extends YoutubeContainer {

    private Uri thumbnail;

    public YoutubeVideo(String url, Uri thumbnail) {
        super(url);

        this.thumbnail = thumbnail;
    }

    public Uri getThumbnail() {
        return thumbnail;
    }

}
