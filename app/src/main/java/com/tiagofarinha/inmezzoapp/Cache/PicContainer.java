package com.tiagofarinha.inmezzoapp.Cache;

import android.graphics.Bitmap;

public class PicContainer {

    private String number;
    private Bitmap pic;

    public PicContainer(String number, Bitmap pic) {
        this.number = number;
        this.pic = pic;
    }

    public String getNumber() {
        return number;
    }

    public Bitmap getPic() {
        return pic;
    }

}
