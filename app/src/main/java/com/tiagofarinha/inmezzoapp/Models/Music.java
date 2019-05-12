package com.tiagofarinha.inmezzoapp.Models;

public class Music {

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
