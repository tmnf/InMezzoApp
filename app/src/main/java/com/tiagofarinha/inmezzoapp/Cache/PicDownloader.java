package com.tiagofarinha.inmezzoapp.Cache;

import android.net.Uri;

import com.squareup.picasso.Picasso;

public class PicDownloader extends Thread {

    /* Handles Profile Pic Download */

    private ResourceLoader rl;
    private String num;
    private Uri uri;

    public PicDownloader(ResourceLoader rl, String num, Uri uri) {
        this.rl = rl;
        this.num = num;
        this.uri = uri;
    }

    @Override
    public void run() {
        getPic();
    }

    private void getPic() {
        try {
            rl.addToPicList(num, Picasso.get().load(uri).get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
