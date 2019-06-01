package com.tiagofarinha.inmezzoapp.Models;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

public class Music implements Adaptable {

    private String name, artist;

    public Music(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public Music() {
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }
}
