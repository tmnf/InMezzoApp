package com.tiagofarinha.inmezzoapp.Adapter;

import android.net.Uri;

import com.squareup.picasso.Picasso;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;

public class PicDownloader extends Thread {

    private ResourceLoader rl;
    private String num;
    private Uri uri;

    public PicDownloader(ResourceLoader rl, String num, Uri uri) {
        this.rl = rl;
        this.num = num;
        this.uri = uri;
    }

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
