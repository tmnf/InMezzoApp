package com.tiagofarinha.inmezzoapp.Models;

import android.graphics.Bitmap;

public class ProfilePic {

    private String number;
    private Bitmap pic;

    public ProfilePic(String number, Bitmap pic) {
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
