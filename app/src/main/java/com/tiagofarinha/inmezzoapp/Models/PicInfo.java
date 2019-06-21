package com.tiagofarinha.inmezzoapp.Models;

import android.net.Uri;

public class PicInfo {

    private Uri uri;
    private int num;

    public PicInfo(Uri uri, int num) {
        this.uri = uri;
        this.num = num;
    }

    public Uri getUri() {
        return uri;
    }

    public int getNum() {
        return num;
    }
}
